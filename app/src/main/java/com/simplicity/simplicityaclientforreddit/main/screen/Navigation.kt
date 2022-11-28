package com.simplicity.simplicityaclientforreddit.main.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.COMMENTS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.HIDDEN_SUBS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.MY_PROFILE
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.POSTS_LIST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.POST_DETAIL
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SEARCH
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SETTINGS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SINGLE_LIST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.TEST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.USER
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.WEB_VIEW
import com.simplicity.simplicityaclientforreddit.main.screen.comments.CommentsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.detail.PostDetailNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.list.PostsListNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.single.SingleListNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.profile.MyProfileNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.search.SearchNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.settings.SettingsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs.HiddenSubsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.test.TestNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.user.UserNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.webview.WebViewNavigation

@Composable
fun Navigation(navigationListener: NavigationListener, navController: NavHostController) {
    NavHost(navController = navController, startDestination = TEST.path) { // SINGLE_LIST POST_DETAIL
        composable(POSTS_LIST.path) {
            PostsListNavigation(navController, navigationListener, "").Launch()
        }
        composable(POSTS_LIST.withArgsFormat(SINGLE_LIST.subReddit), NavRoute.getArguments(SINGLE_LIST.subReddit)) { stack ->
            PostsListNavigation(navController, navigationListener, stack.arg(SINGLE_LIST.subReddit)).Launch()
        }
        composable(SINGLE_LIST.path, NavRoute.getArguments(SINGLE_LIST.subReddit)) {
            SingleListNavigation(navController, navigationListener, "").Launch()
        }
        composable(SINGLE_LIST.withArgsFormat(SINGLE_LIST.subReddit), NavRoute.getArguments(SINGLE_LIST.subReddit)) { stack ->
            SingleListNavigation(navController, navigationListener, stack.arg(SINGLE_LIST.subReddit)).Launch()
        }
        composable(POST_DETAIL.path) { PostDetailNavigation(navController).Launch() }
        composable(SETTINGS.path) { SettingsNavigation(navController).Launch() }
        composable(MY_PROFILE.path) { MyProfileNavigation(navController).Launch() }
        composable(USER.withArgsFormat(USER.userName), NavRoute.getArguments(USER.userName)) { stack ->
            UserNavigation(navController, navigationListener, stack.arg(USER.userName)).Launch()
        }
        composable(SEARCH.path) { SearchNavigation(navController).Launch() }
        composable(
            COMMENTS.withArgsFormat(COMMENTS.postId, COMMENTS.subReddit),
            NavRoute.run { getArguments(listOf(COMMENTS.postId, COMMENTS.subReddit)) }
        ) { stack ->
            CommentsNavigation(navController, stack.arg(COMMENTS.postId), stack.arg(COMMENTS.subReddit)).Launch()
        }
        composable(TEST.path) { TestNavigation(navController).Launch() }
        composable(HIDDEN_SUBS.path) { HiddenSubsNavigation(navController).Launch() }
        composable(WEB_VIEW.withArgsFormat(WEB_VIEW.url), NavRoute.getArguments(WEB_VIEW.url)) { stack ->
            WebViewNavigation(navController, stack.arg(WEB_VIEW.url)).Launch()
        }
    }
}

fun NavBackStackEntry.arg(keyName: String): String {
    return arguments?.getString(keyName) ?: ""
}

sealed class NavRoute(val path: String) {
    object POSTS_LIST : NavRoute("posts_list") {
        const val subReddit = "sub_reddit"
    }

    object SINGLE_LIST : NavRoute("single_list") {
        const val subReddit = "sub_reddit"
    }

    object POST_DETAIL : NavRoute("post_detail")
    object SETTINGS : NavRoute("settings")
    object MY_PROFILE : NavRoute("my_profile")
    object USER : NavRoute("user") {
        const val userName = "user_name"
    }
    object SEARCH : NavRoute("search")
    object HIDDEN_SUBS : NavRoute("hiddenSubs")
    object WEB_VIEW : NavRoute("link") {
        const val url = "url"
    }

    object COMMENTS : NavRoute("comments") {
        const val postId = "post_id"
        const val subReddit = "sub_reddit"
    }

    object TEST : NavRoute("test")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
//                append("/{$arg}")
                append("/{$arg}")
            }
        }
    }

    companion object {
        fun getArguments(argName: String): List<NamedNavArgument> {
            return listOf(navArgument(argName) { type = NavType.StringType })
        }

        fun getArguments(argsName: List<String>): List<NamedNavArgument> {
            val list = ArrayList<NamedNavArgument>()
            for (arg in argsName) {
                list.add(navArgument(arg) { type = NavType.StringType })
            }
            return list
        }
    }
}
