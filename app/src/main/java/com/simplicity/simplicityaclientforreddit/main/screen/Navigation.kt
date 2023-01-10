package com.simplicity.simplicityaclientforreddit.main.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.AUTHENTICATION
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.AUTHENTICATION_RESULT
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.COMMENTS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.FULL_SCREEN_IMAGE
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.HIDDEN_SUBS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.MENU
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.MY_PROFILE
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.POSTS_LIST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.POST_DETAIL
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SEARCH
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SETTINGS
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SINGLE_LIST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.SPLASH
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.TEST
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.USER
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.WEB_VIEW
import com.simplicity.simplicityaclientforreddit.main.screen.authenticate.AuthenticateNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.authenticate.result.AuthenticationResultNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.comments.CommentsInput
import com.simplicity.simplicityaclientforreddit.main.screen.comments.CommentsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.menu.MenuNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.detail.PostDetailNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.fullscreen.image.FullScreenImageInput
import com.simplicity.simplicityaclientforreddit.main.screen.posts.fullscreen.image.FullScreenImageNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.list.PostsListNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.posts.single.SingleListNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.profile.MyProfileNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.search.SearchNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.settings.SettingsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs.HiddenSubsNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.splash.SplashNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.test.TestNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.user.UserNavigation
import com.simplicity.simplicityaclientforreddit.main.screen.webview.WebViewNavigation
import com.simplicity.simplicityaclientforreddit.main.utils.extensions.arg
import com.simplicity.simplicityaclientforreddit.main.utils.extensions.route

@Composable
fun Navigation(navigationListener: NavigationListener, navigator: NavHostController) {
    NavHost(navController = navigator, startDestination = SPLASH.path) {
        route(SPLASH) { SplashNavigation(navigator, navigationListener).Launch() }
        route(POSTS_LIST) {
            PostsListNavigation(navigator, navigationListener, "").Launch()
        }
        route(POSTS_LIST, listOf(POSTS_LIST.subReddit)) { stack ->
            PostsListNavigation(navigator, navigationListener, stack.arg(POSTS_LIST.subReddit)).Launch()
        }
        route(SINGLE_LIST, listOf(SINGLE_LIST.subReddit)) {
            SingleListNavigation(navigator, navigationListener, "").Launch()
        }
        route(SINGLE_LIST, listOf(SINGLE_LIST.subReddit)) { stack ->
            SingleListNavigation(navigator, navigationListener, stack.arg(SINGLE_LIST.subReddit)).Launch()
        }
        route(POST_DETAIL) { PostDetailNavigation(navigator, navigationListener).Launch() }
        route(SETTINGS) { SettingsNavigation(navigator, navigationListener).Launch() }
        route(MY_PROFILE) { MyProfileNavigation(navigator).Launch() }
        route(USER, listOf(USER.userName)) { stack ->
            UserNavigation(navigator, navigationListener, stack.arg(USER.userName)).Launch()
        }
        route(SEARCH) { SearchNavigation(navigator, navigationListener).Launch() }
        route(COMMENTS, listOf(COMMENTS.postId, COMMENTS.subReddit)) { stack ->
            CommentsNavigation(navigator, navigationListener).Launch(
                CommentsInput(
                    stack.arg(COMMENTS.postId),
                    stack.arg(COMMENTS.subReddit)
                )
            )
        }
        route(TEST) { TestNavigation(navigator, navigationListener).Launch() }
        route(MENU) { MenuNavigation(navigator, navigationListener).Launch() }
        route(AUTHENTICATION) { AuthenticateNavigation(navigator, navigationListener).Launch() }
        route(AUTHENTICATION_RESULT) { AuthenticationResultNavigation(navigator, navigationListener).Launch() }
        route(HIDDEN_SUBS) { HiddenSubsNavigation(navigator).Launch() }
        route(WEB_VIEW, listOf(WEB_VIEW.url)) { stack ->
            WebViewNavigation(navigator, stack.arg(WEB_VIEW.url)).Launch()
        }
        route(FULL_SCREEN_IMAGE, listOf(FULL_SCREEN_IMAGE.url)) { stack ->
            FullScreenImageNavigation(navigator, navigationListener).Launch(FullScreenImageInput(stack.arg(FULL_SCREEN_IMAGE.url)))
        }
    }
}

sealed class NavRoute(val path: String) {
    object SPLASH : NavRoute("splash")
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

    object FULL_SCREEN_IMAGE : NavRoute("full_screen_image") {
        const val url = "url"
    }

    object COMMENTS : NavRoute("comments") {
        const val postId = "post_id"
        const val subReddit = "sub_reddit"
    }

    object TEST : NavRoute("test")
    object MENU : NavRoute("menu")
    object AUTHENTICATION : NavRoute("authentication")
    object AUTHENTICATION_RESULT : NavRoute("authentication_result")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String?): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {

        fun getArguments(argsName: List<String>): List<NamedNavArgument> {
            val list = ArrayList<NamedNavArgument>()
            for (arg in argsName) {
                list.add(navArgument(arg) { type = NavType.StringType })
            }
            return list
        }
    }
}
