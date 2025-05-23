package com.example.newsapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentArticleDetailBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.loadImage

class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayArticleDetails(args.article)
    }

    private fun displayArticleDetails(article: Article) {
        with(binding) {
            articleImage.loadImage(article.urlToImage)
            articleTitle.text = article.title
            articleSource.text = article.source
            articleDate.text = article.publishedAt
            articleContent.text = article.content
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}