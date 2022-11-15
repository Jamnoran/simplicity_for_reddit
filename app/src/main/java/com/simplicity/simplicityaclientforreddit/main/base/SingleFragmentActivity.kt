package com.simplicity.simplicityaclientforreddit.main.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.fragments.authentication.AuthenticationFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.comments.CommentsFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.settings.SettingsFragment
import com.simplicity.simplicityaclientforreddit.main.fragments.settings.hiddenSubs.HiddenSubsFragment
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP.Companion.KEY_AUTHOR
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP.Companion.KEY_ID
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP.Companion.KEY_SUB_REDDIT

class SingleFragmentActivity : BaseActivity() {
    private val TAG: String = "SingleFragmentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        intent.extras?.let {
            Log.i(TAG, "Starting fragment ${it.getString(KEY_FRAGMENT)}")
            when (it.getString(KEY_FRAGMENT)) {
                FRAGMENT_VALUE_COMMENT -> {
                    Log.i(
                        TAG,
                        "Starting CommentFragment with ${it.getString(KEY_SUB_REDDIT)}  -- ${
                        it.getString(KEY_ID)
                        }"
                    )
                    it.getString(KEY_SUB_REDDIT)?.let { subRedditKey ->
                        it.getString(KEY_ID)?.let { keyId ->
                            it.getString(KEY_AUTHOR)?.let { author ->
                                startFragment(
                                    CommentsFragment.newInstance(
                                        subRedditKey,
                                        keyId,
                                        author
                                    )
                                )
                            }
                        }
                    }
                }
                FRAGMENT_VALUE_AUTHENTICATION -> {
                    startFragment(AuthenticationFragment())
                }
                FRAGMENT_VALUE_SETTINGS -> {
                    startFragment(SettingsFragment())
                }
                FRAGMENT_VALUE_SETTINGS_HIDDEN_SUBS -> {
                    startFragment(HiddenSubsFragment())
                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val f: Fragment? =
            supportFragmentManager.findFragmentById(R.id.container)
        if (f is AuthenticationFragment) {
            Log.i(TAG, "OnResume is called and is authentication fragment, lets close this screen")
            finish()
        }
    }

    companion object {
        const val KEY_FRAGMENT = "fragment_key"
        const val FRAGMENT_VALUE_COMMENT = "fragment_comment"
        const val FRAGMENT_VALUE_AUTHENTICATION = "fragment_authentication"
        const val FRAGMENT_VALUE_SETTINGS = "fragment_settings"
        const val FRAGMENT_VALUE_SETTINGS_HIDDEN_SUBS = "fragment_settings_hidden_subs"
    }
}
