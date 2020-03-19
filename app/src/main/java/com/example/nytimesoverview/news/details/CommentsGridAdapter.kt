package com.example.nytimesoverview.news.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimesoverview.databinding.CommentGridItemBinding
import com.example.nytimesoverview.news.overview.model.NewsComment

class CommentsGridAdapter : ListAdapter<NewsComment, CommentsGridAdapter.NewsCommentsViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsGridAdapter.NewsCommentsViewHolder {
        return NewsCommentsViewHolder(
            CommentGridItemBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CommentsGridAdapter.NewsCommentsViewHolder, position: Int) {
        val newsComment = getItem(position)
        holder.bind(newsComment)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsComment>() {

        override fun areItemsTheSame(oldItem: NewsComment, newItem: NewsComment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsComment, newItem: NewsComment): Boolean {
            return oldItem.comment == newItem.comment
        }
    }

    class NewsCommentsViewHolder(private var binding: CommentGridItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsComment: NewsComment) {
            binding.newsComment = newsComment
            binding.executePendingBindings()
        }
    }

}