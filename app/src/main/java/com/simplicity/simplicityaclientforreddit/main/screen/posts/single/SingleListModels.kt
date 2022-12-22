package com.simplicity.simplicityaclientforreddit.main.screen.posts.single

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost

data class Data(val redditPost: RedditPost? = null, val scrollToTop: Unit? = null)

class SingleListInput(val subReddit: String, val navigationListener: NavigationListener) : BaseLogic.BaseInput
