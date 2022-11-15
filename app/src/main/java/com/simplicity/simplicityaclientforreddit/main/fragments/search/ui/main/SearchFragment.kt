package com.simplicity.simplicityaclientforreddit.main.fragments.search.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.simplicity.simplicityaclientforreddit.databinding.SearchFragmentBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.main.base.Mapper
import com.simplicity.simplicityaclientforreddit.main.fragments.search.SearchActivity
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.GetSubRedditIntentUseCase
import com.simplicity.simplicityaclientforreddit.main.viewholders.mapToTextViewHolderList

class SearchFragment : BaseTestFragment<SearchFragmentBinding, SeachViewModel>
(SeachViewModel::class.java, SearchFragmentBinding::inflate) {

    companion object {
        fun newInstance() = SearchFragment()
        private const val TAG = "SearchFragment"
    }

    override fun ready(savedInstanceState: Bundle?) {
        setUpAdapter(binding.recyclerView, resources, requireContext())
        viewModel = ViewModelProvider(this).get(SeachViewModel::class.java)
        viewModel.subreddits().observe(requireActivity()) { observeSubreddits(it) }
        viewModel.isFetching().observe(requireActivity()) { observeFetching(it) }
        setUpListeners()
        binding.loading.visibility = View.GONE
    }

    private fun observeFetching(it: Boolean) {
        if (it) binding.loading.visibility = View.VISIBLE else binding.loading.visibility = View.GONE
    }

    private fun setUpListeners() {
        binding.searchTextInputField.addTextChangedListener { text ->
            Log.i(TAG, "Text is changed to $text")
            viewModel.searchInputChanged(text.toString())
        }
        binding.nsfwListButton.setOnClickListener {
            viewModel.fetchNsfwSubReddits()
        }
    }

    private fun observeSubreddits(list: List<String>) {
        Log.i(TAG, "---- First call to observeSubreddits")
        val wrappedList = Mapper().mapToTextViewHolderList(list) { subReddit ->
            subRedditClicked(subReddit)
        }
        submitList(wrappedList as ArrayList<Any>)
        Log.i(TAG, "---- Added all subreddits")
    }

    private fun subRedditClicked(subReddit: String) {
        Log.i(TAG, "Clicked on subreddit $subReddit")
        viewModel.subRedditClicked(subReddit)
        (activity as SearchActivity).startActivityWithAnimation(
            GetSubRedditIntentUseCase(
                subReddit,
                requireActivity()
            ).execute()
        )
    }
}
