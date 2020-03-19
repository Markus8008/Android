package com.example.nytimesoverview

import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.nytimesoverview.network.NewsPeriodFilter
import com.example.nytimesoverview.news.details.CommentsGridAdapter
import com.example.nytimesoverview.news.overview.NewsGridAdapter
import com.example.nytimesoverview.news.overview.model.NewsComment
import com.example.nytimesoverview.news.overview.model.NewsProperties
import com.example.nytimesoverview.news.overview.model.NewsViewModel

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<NewsProperties>?) {
    val adapter = recyclerView.adapter as NewsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("listComments")
fun bindCommentsRecyclerView(recyclerView: RecyclerView,
                     data: List<NewsComment>?) {
    val adapter = recyclerView.adapter as CommentsGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("newsApiStatus")
fun bindStatus(statusImageView: ImageView,
               status: NewsViewModel.NYTimesNewsApiStatus?) {

    when(status) {
        NewsViewModel.NYTimesNewsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsViewModel.NYTimesNewsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsViewModel.NYTimesNewsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}