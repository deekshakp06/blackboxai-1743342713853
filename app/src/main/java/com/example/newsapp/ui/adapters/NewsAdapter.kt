package com.example.newsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.loadImage

class NewsAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onSaveClick: (Article) -> Unit,
    private val onShareClick: (Article) -> Unit
) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(binding) {
                articleImage.loadImage(article.urlToImage)
                articleTitle.text = article.title
                articleSource.text = root.context.getString(R.string.source, article.source)
                articleDate.text = root.context.getString(R.string.published_at, article.publishedAt)
                
                btnSave.setImageResource(
                    if (article.isSaved) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border
                )
                
                root.setOnClickListener { onItemClick(article) }
                btnSave.setOnClickListener { onSaveClick(article) }
                btnShare.setOnClickListener { onShareClick(article) }
            }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}