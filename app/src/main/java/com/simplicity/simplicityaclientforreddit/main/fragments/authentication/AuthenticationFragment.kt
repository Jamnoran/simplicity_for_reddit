package com.simplicity.simplicityaclientforreddit.main.fragments.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.databinding.AuthenticationFragmentBinding

class AuthenticationFragment : BaseTestFragment<AuthenticationFragmentBinding, AuthenticationViewModel>
(AuthenticationViewModel::class.java, AuthenticationFragmentBinding::inflate) {

    private val AUTH_URL = "https://www.reddit.com/api/v1/authorize.compact?client_id=%s" +
        "&response_type=code&state=%s&redirect_uri=%s&" +
        "duration=permanent&scope=identity,edit,flair,history,mysubreddits,privatemessages,read,report,save,submit,subscribe,vote,creddits"

    private val CLIENT_ID = "5IyfyqHZKHflfxTAKUj3zg"

    private val REDIRECT_URI = "simplicity://com.simplicity/redirect"

    private val STATE = "MY_RANDOM_STRING_1"

    companion object {
        fun newInstance() = AuthenticationFragment()
    }

    override fun ready(savedInstanceState: Bundle?) {
        startSignIn()
    }

    private fun startSignIn() {
        val url = String.format(AUTH_URL, CLIENT_ID, STATE, REDIRECT_URI)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    fun getAccessToken(code: String) {
        Log.i("AuthenticationFragment", "Code is $code")
    }

    fun isCorrectState(state: String): Boolean {
        return state == STATE
    }
}
