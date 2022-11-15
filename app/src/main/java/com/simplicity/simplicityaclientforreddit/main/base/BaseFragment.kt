package com.simplicity.simplicityaclientforreddit.main.base

//open class BaseFragment : Fragment() {
//
//    private lateinit var _viewModel: BaseViewModel<VB: ViewBinding>
//
//    fun init(viewModel: BaseViewModel<VB>) {
//        _viewModel = viewModel
//        _viewModel.networkError().observe(this) { observeNetworkError() }
//        _viewModel.unAuthorizedError().observe(this) { observeUnAuthorizedError() }
//    }
//
//    private fun observeNetworkError() {
//        Toast.makeText(requireContext(), getString(R.string.error_network), Toast.LENGTH_LONG).show()
//    }
//
//    private fun observeUnAuthorizedError() {
//        Toast.makeText(requireContext(), getString(R.string.error_un_authorized), Toast.LENGTH_LONG).show()
//    }
//
//    open fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return false
//    }
//
//    fun observeHideSub() {
//        Toast.makeText(requireContext(), "Sub is now hidden", Toast.LENGTH_LONG).show()
//    }
//
//    fun observeOpenCommentsFragment(post: RedditPost) {
//        val intent = Intent(activity, SingleFragmentActivity::class.java).apply {
//            putExtra(SingleFragmentActivity.KEY_FRAGMENT, SingleFragmentActivity.FRAGMENT_VALUE_COMMENT)
//            putExtra(KEY_SUB_REDDIT, post.data.subreddit)
//            putExtra(KEY_AUTHOR, post.data.author)
//            putExtra(KEY_ID, post.data.id)
//        }
//        (activity as MainActivity).startActivityWithAnimation(intent)
//    }
//
//    fun observeSubredditClicked(it: String) {
//        val intent = Intent(context, MainActivity::class.java)
//        intent.putExtra(MainActivity.KEY_SUBREDDIT, it)
//        (activity as BaseActivity).startActivityWithAnimation(intent)
//    }
//
//    fun observeAuthorClicked(it: String) {
//        val intent = Intent(context, UserDetailActivity::class.java)
//        intent.putExtra(UserDetailActivity.KEY_USER_NAME, it)
//        (activity as BaseActivity).startActivityWithAnimation(intent)
//    }
//
//    fun observeRedditLinkClicked(it: String) {
//        val convertedUrl = "https://www.reddit.com$it"
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(convertedUrl))
//        startActivity(browserIntent)
//    }
//
//    fun observeSendToBrowser(url: String) {
//        val i = Intent(Intent.ACTION_VIEW)
//        i.data = Uri.parse(url)
//        Log.i("ListFragment", "Sending this url to browser: $url")
//        startActivity(i)
//    }
//
//    fun observeWebViewActivityClicked(url: String) {
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(browserIntent)
//    }
//}
