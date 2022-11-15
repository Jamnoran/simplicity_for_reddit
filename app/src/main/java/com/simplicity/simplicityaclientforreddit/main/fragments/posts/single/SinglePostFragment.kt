package com.simplicity.simplicityaclientforreddit.main.fragments.posts.single

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import com.simplicity.simplicityaclientforreddit.databinding.FragmentSinglePostBinding
import com.simplicity.simplicityaclientforreddit.databinding.MediaVideoPlayerBinding
import com.simplicity.simplicityaclientforreddit.main.MainActivity
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.adapter.customViews.RedditMedia
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.util.RedditPostListenerImpl
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.usecases.post.GetPostTypeUseCase

class SinglePostFragment : BaseTestFragment<FragmentSinglePostBinding, SinglePostViewModel>
(SinglePostViewModel::class.java, FragmentSinglePostBinding::inflate) {

    private val singlePostUrl = ""

//    val singlePostUrl = "https://www.reddit.com/r/perfectlycutscreams/comments/xw6jwa/this_distinguished_gentleman_had_an_unfortunate/"
//    val singlePostUrl = "/r/WhitePeopleTwitter/comments/owsdei/i_love_this_guy/"
    val nextTestPostUrl = "/r/leagueoflegends/comments/xca6dv/challenged_the_enemy_udyr_to_a_1v1_but_sadly_it/"
//    val nextTestPostUrl = "/r/videos/comments/xeacgi/nintendo_direct_vs_sony_direct_dunkey/"

    override fun ready(savedInstanceState: Bundle?) {
        arguments?.getString(MainActivity.KEY_SUBREDDIT)?.let {
            viewModel.setSubReddit(it)
            Toast.makeText(requireContext(), "Fetching sub reddit $it", Toast.LENGTH_LONG).show()
        }
        log("Starting fragment")
        if (singlePostUrl.isNotEmpty()) {
            viewModel.fetchPost(singlePostUrl)
        } else {
            viewModel.fetchPosts()
        }
        binding.scrollToNext.setOnClickListener {
            viewModel.showNextPost()
//            viewModel.fetchPost(nextTestPostUrl)
        }
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.post().observe(requireActivity()) { observeRedditPost(it) }
        viewModel.hideSub().observe(requireActivity()) { observeHideSub() }
        viewModel.openCommentsFragment().observe(requireActivity()) { observeOpenCommentsFragment(it) }
        viewModel.shareRedditClicked().observe(requireActivity()) { observeShareReddit(it) }
        viewModel.subRedditClicked().observe(requireActivity()) { observeSubredditClicked(it) }
        viewModel.authorClicked().observe(requireActivity()) { observeAuthorClicked(it) }
        viewModel.redditUrlClicked().observe(requireActivity()) { observeRedditLinkClicked(it) }
        viewModel.webViewActivityClicked().observe(requireActivity()) { observeWebViewActivityClicked(it) }
        viewModel.browserUrlClicked().observe(requireActivity()) { observeSendToBrowser(it) }
        viewModel.initVideoPlayer().observe(requireActivity()) { observeInitVideoPlayer(it) }
    }

    private fun observeRedditPost(post: RedditPost) {
        log("Post type : ${GetPostTypeUseCase().execute(post.data)}")
        log("Observed reddit post: $post")
        binding.redditMedia.redditMedia.redditMediaHolder.removeAllViews()
        view?.let {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            RedditMedia(post, RedditPostListenerImpl(viewModel)).init(binding.redditMedia, layoutInflater)
        }
        if (post.data.postHint != "link") {
            binding.redditMedia.redditTitle.text = post.data.title
        } else {
            binding.redditMedia.redditTitle.visibility = View.GONE
        }
    }

    private fun observeInitVideoPlayer(mediaVideoPlayerBinding: MediaVideoPlayerBinding) {
        Log.i(TAG, "Wohoo")
    }

    companion object {
        fun newInstance(subReddit: String?): SinglePostFragment {
            val args = Bundle()
            args.putString(MainActivity.KEY_SUBREDDIT, subReddit)
            val fragment = SinglePostFragment()
            fragment.arguments = args
            return fragment
        }

        private val TAG = SinglePostFragment::class.java.name
    }
}
