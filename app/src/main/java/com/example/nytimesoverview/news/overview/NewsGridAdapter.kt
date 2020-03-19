package com.example.nytimesoverview.news.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nytimesoverview.databinding.NewsGridItemBinding
import com.example.nytimesoverview.news.overview.model.NewsProperties

class NewsGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<NewsProperties, NewsGridAdapter.NewsPropertiesViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsGridAdapter.NewsPropertiesViewHolder {
        return NewsPropertiesViewHolder(NewsGridItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsGridAdapter.NewsPropertiesViewHolder, position: Int) {
        val newsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(newsProperty)
        }
        holder.bind(newsProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsProperties>() {

        override fun areItemsTheSame(oldItem: NewsProperties, newItem: NewsProperties): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NewsProperties, newItem: NewsProperties): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class NewsPropertiesViewHolder(private var binding: NewsGridItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsProperties: NewsProperties) {
            binding.property = newsProperties
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (newsProperties: NewsProperties) -> Unit) {
        fun onClick(newsProperties: NewsProperties) { clickListener(newsProperties)}
    }
}