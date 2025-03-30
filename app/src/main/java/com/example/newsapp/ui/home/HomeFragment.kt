package com.example.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupObservers()
        setupListeners()
        setupCategoryFab()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            onItemClick = { article ->
                val action = HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(article)
                findNavController().navigate(action)
            },
            onSaveClick = { article ->
                if (article.isSaved) {
                    viewModel.deleteArticle(article)
                } else {
                    viewModel.saveArticle(article)
                }
            },
            onShareClick = { article ->
                shareArticle(article)
            }
        )
        
        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.newsFlow.collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        newsAdapter.submitList(result.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showError(result.message)
                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshNews()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupCategoryFab() {
        binding.fabCategory = fab_category
        fab_category.setOnClickListener {
            showCategoryBottomSheet()
        }
        
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCurrentCategory()?.let { category ->
                binding.categoryChip.apply {
                    text = getString(when(category) {
                        Category.GENERAL -> R.string.category_general
                        Category.BUSINESS -> R.string.category_business
                        Category.ENTERTAINMENT -> R.string.category_entertainment
                        Category.HEALTH -> R.string.category_health
                        Category.SCIENCE -> R.string.category_science
                        Category.SPORTS -> R.string.category_sports
                        Category.TECHNOLOGY -> R.string.category_technology
                    })
                    visibility = View.VISIBLE
                    setOnCloseIconClickListener {
                        viewModel.getTopHeadlines()
                        visibility = View.GONE
                    }
                }
            } ?: run {
                binding.categoryChip.visibility = View.GONE
            }
        }
    }

    private fun showCategoryBottomSheet() {
        CategoryBottomSheet { category ->
            viewModel.getTopHeadlines(category.value)
        }.show(parentFragmentManager, "CategoryBottomSheet")
    }

    private fun shareArticle(article: Article) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${article.title}\n\n${article.url}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share article via"))
    }

    private fun showError(message: String?) {
        Snackbar.make(binding.root, message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}