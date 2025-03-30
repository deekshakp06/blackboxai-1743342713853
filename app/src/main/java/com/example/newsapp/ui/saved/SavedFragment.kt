package com.example.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentSavedBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SavedViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            onItemClick = { article ->
                val action = SavedFragmentDirections.actionSavedFragmentToArticleDetailFragment(article)
                findNavController().navigate(action)
            },
            onSaveClick = { article ->
                viewModel.deleteArticle(article)
            },
            onShareClick = { article ->
                // Share functionality
            }
        )

        binding.recyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.savedArticles.collectLatest { articles ->
                newsAdapter.submitList(articles)
                binding.emptyView.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}