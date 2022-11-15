package com.simplicity.simplicityaclientforreddit.main

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.MainActivityBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseActivity
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.menu.SideMenuBar
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.ListFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.single.SinglePostFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.webview.WebViewActivity
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import com.simplicity.simplicityaclientforreddit.main.usecases.firebase.FireBaseLogUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.subreddits.GetSubRedditIntentUseCase

class MainActivity : BaseActivity() {
    companion object {
        const val AUTHENTICATION_DONE = 1001
        const val KEY_SUBREDDIT = "subreddit"
    }

    private lateinit var sideMenu: SideMenuBar
    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    private var _currentFragment: Fragment? = null
    private var _subreddit: String? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(binding.root)
        com.simplicity.simplicityaclientforreddit.main.Global.applicationContext = applicationContext

        if (savedInstanceState == null) {
            intent.extras?.let {
                _subreddit = it.getString(KEY_SUBREDDIT)
            }
            setUpObservables()
            viewModel.initApplication()
            val displayMetrics = DisplayMetrics()
            this.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            viewModel.setDeviceInfo(displayMetrics.widthPixels, displayMetrics.heightPixels)
            navigateToScreen()
        }
        sideMenu = SideMenuBar(this, binding.navigationDrawer)
        sideMenu.init()

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        Log.i("MainActivity", "Refresh token ${SettingsSP().loadSetting(SettingsSP.KEY_REFRESH_TOKEN, "Default")}")
        mFirebaseAnalytics?.let { FireBaseLogUseCase(it).execute("app_started", "MainActivity", "logged_in_false") }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        Log.i("MainActivity", "onKeyDown $keyCode $event")
        return if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            _currentFragment?.let { it ->
                val acceptsBackButton = (_currentFragment as BaseTestFragment<*, *>).onKeyDown(keyCode, event)
                if (!acceptsBackButton || _subreddit != null) {
                    goBack()
                }
            }
            false
        } else super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
        updateUiFromResume()
    }

    private fun updateUiFromResume() {
        sideMenu.update()
        if (SettingsSP().loadSetting(SettingsSP.KEY_CODE, null) != null) {
            viewModel.fetchAuthenticationToken()
        }
    }

    fun subRedditClicked(subreddit: String) {
        startActivityWithAnimation(GetSubRedditIntentUseCase(subreddit, this).execute())
        viewModel.fetchListOfVisitedSubReddits()
    }

    fun startWebViewActivity(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.apply { putExtra("URL", url) }
        startActivity(intent)
    }

    private fun navigateToScreen() {
        if (SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, true)) {
            startF(ListFragment.newInstance(_subreddit))
        } else {
            startF(SinglePostFragment.newInstance(_subreddit))
        }
//        startFragment(TestFragment.newInstance())
//            startFragment(DetailFragment.newInstance())
//            startWebViewActivity("https://i.imgur.com/MBtV8jD.gifv")
//            startWebViewActivity("https://www.redditmedia.com/mediaembed/ptnj5n")
//            startWebViewActivity("https://i.imgur.com/gK1zKGP.gifv")
//            startWebViewActivity("http://jamnoran.se/test2.html")

//        val intent = Intent(this, UserDetailActivity::class.java)
//        intent.putExtra(UserDetailActivity.KEY_USER_NAME, "PantyNectar")
//        startActivityWithAnimation(intent)
    }

    private fun setUpObservables() {
        viewModel.accessToken().observe(this) { observeAccessToken(it) }
        viewModel.refreshToken().observe(this) { observeRefreshToken(it) }
        viewModel.user().observe(this) { observeUser(it) }
    }

    private fun observeUser(it: User) {
        Log.i("MainActivity", "User is fetched and we now fetch firebase user")
//        viewModel.getFirebaseUser()
    }

    private fun observeAccessToken(it: String) {
        SettingsSP().saveSetting(SettingsSP.KEY_ACCESS_TOKEN, it)
        sideMenu.update()
    }

    private fun observeRefreshToken(it: String) {
        SettingsSP().saveSetting(SettingsSP.KEY_REFRESH_TOKEN, it)
    }

    private fun startF(fragment: Fragment) {
        _currentFragment = fragment
        _currentFragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, it)
                .commitNow()
        }
    }

    fun closeDrawer() {
        findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawers()
    }
}
