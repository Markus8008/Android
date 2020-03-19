package com.example.nytimesoverview.news.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nytimesoverview.databinding.NewsDetailsFragmentBinding


class NewsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = NewsDetailsFragmentBinding`.inflate(inflater)
        binding.setLifecycleOwner(this)

        val newsProperties = NewsDetailsFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = NewsDetaillViewModelFactory(newsProperties, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(NewsDetailsViewModel::class.java)

        binding.addCommentButton.setOnClickListener {
            onAddComment(it, binding)
        }

        binding.commentsList.adapter = CommentsGridAdapter()
        return binding.root
    }

    private fun onAddComment(view: View, binding: NewsDetailsFragmentBinding?) {
        binding?.apply {
            viewModel?.onAddComment(commentText?.text.toString())
            commentText.text.clear()
        }
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)
    }

}
