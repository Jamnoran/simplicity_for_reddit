package com.simplicity.simplicityaclientforreddit.main.base

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.MainActivity
import com.simplicity.simplicityaclientforreddit.main.fragments.user.UserDetailActivity
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

abstract class BaseTestFragment<VB : ViewBinding, VM : BaseViewModel<VB>>(
    private val viewModelClass: Class<VM>,
    private val bindingInflater: (layoutInflater: LayoutInflater) -> VB
) : Fragment() {
    val adaptView = AdaptView()
    lateinit var viewModel: VM
    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    open var useSharedViewModel: Boolean = false

    abstract fun ready(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return _binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity()).get(
                viewModelClass
            )
        } else {
            ViewModelProvider(this).get(viewModelClass)
        }
        viewModel.initBinding(binding)

        viewModel.networkError().observe(viewLifecycleOwner) { observeNetworkError() }
        viewModel.unAuthorizedError().observe(viewLifecycleOwner) { observeUnAuthorizedError() }

        ready(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun log(log: String) {
        Log.i("[${this::class.java.simpleName}]", log)
    }

    private fun observeNetworkError() {
        Toast.makeText(requireContext(), getString(R.string.error_network), Toast.LENGTH_LONG)
            .show()
    }

    private fun observeUnAuthorizedError() {
        Toast.makeText(requireContext(), getString(R.string.error_un_authorized), Toast.LENGTH_LONG)
            .show()
    }

    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return false
    }

    fun observeHideSub() {
        Toast.makeText(requireContext(), "Sub is now hidden", Toast.LENGTH_LONG).show()
    }

    fun observeOpenCommentsFragment(post: RedditPost) {
        val intent = Intent(activity, SingleFragmentActivity::class.java).apply {
            putExtra(
                SingleFragmentActivity.KEY_FRAGMENT,
                SingleFragmentActivity.FRAGMENT_VALUE_COMMENT
            )
            putExtra(SettingsSP.KEY_SUB_REDDIT, post.data.subreddit)
            putExtra(SettingsSP.KEY_AUTHOR, post.data.author)
            putExtra(SettingsSP.KEY_ID, post.data.id)
        }
        (activity as MainActivity).startActivityWithAnimation(intent)
    }

    fun observeSubredditClicked(it: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(MainActivity.KEY_SUBREDDIT, it)
        (activity as BaseActivity).startActivityWithAnimation(intent)
    }

    fun observeShareReddit(redditPost: RedditPost?) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, redditPost?.data?.url)
        startActivity(Intent.createChooser(shareIntent, "Share link using"))
    }

    fun observeAuthorClicked(it: String) {
        val intent = Intent(context, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.KEY_USER_NAME, it)
        (activity as BaseActivity).startActivityWithAnimation(intent)
    }

    fun observeRedditLinkClicked(it: String) {
        val convertedUrl = "https://www.reddit.com$it"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
        startActivity(browserIntent)
    }

    fun observeSendToBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        Log.i("ListFragment", "Sending this url to browser: $url")
        startActivity(i)
    }

    fun observeWebViewActivityClicked(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    fun setUpAdapter(recyclerView: RecyclerView, resources: Resources, context: Context) {
        adaptView.setUpAdapter(recyclerView, resources, context)
    }

    fun submitList(arrayList: java.util.ArrayList<Any>) {
        adaptView.submitList(arrayList)
    }

    fun startActivityWithAnimation(intent: Intent) {
        val activityAsBase = activity as BaseActivity
        activityAsBase.startActivityWithAnimation(intent)
    }

    fun startSingleActivity(fragmentValue: String) {
        val intent = Intent(activity, SingleFragmentActivity::class.java).apply {
            putExtra(SingleFragmentActivity.KEY_FRAGMENT, fragmentValue)
        }
        startActivityWithAnimation(intent)
    }
}
