package com.simplicity.simplicityaclientforreddit.main.base.compose

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

open class BaseComposeLogic<Input> : BaseLogic() {
    private var started = false
    private var navigator: NavHostController? = null
    private var navListener: NavigationListener? = null

    fun init(input: Input? = null, navController: NavHostController, navigationListener: NavigationListener) {
        navigator = navController
        navListener = navigationListener
        if (!checkIfStarted()) {
            if (input != null) {
                ready(input)
            } else {
                ready()
            }
        }
    }

    fun loggI(log: String) {
        Log.i(className(), log)
    }

    fun loggD(log: String) {
        Log.d(className(), log)
    }

    fun loggW(log: String, throwable: Throwable? = null) {
        Log.w(className(), log, throwable)
    }

    fun loggE(log: String, throwable: Throwable? = null) {
        Log.e(className(), log, throwable)
    }

    fun className(): String = this::class.simpleName ?: "WRONG"

    open fun ready(input: Input) {
    }

    open fun ready() {
    }

    fun checkIfStarted(): Boolean {
        val valueOfFirstStarted = started
        started = true
        return valueOfFirstStarted
    }

    fun navigate(): NavHostController? = navigator

    fun navListener(intent: Intent) = navListener?.navigate?.invoke(intent)

    fun openBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        navListener?.navigate?.invoke(browserIntent)
    }
}
