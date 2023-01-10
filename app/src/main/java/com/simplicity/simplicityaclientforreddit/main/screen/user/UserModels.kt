package com.simplicity.simplicityaclientforreddit.main.screen.user

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User

data class Data(val user: User? = null, val posts: List<RedditPost> = emptyList()) {
    companion object {
        fun preview(): Data {
            return Data(user = TesterHelper.getUser(), posts = listOf(TesterHelper.getPost()))
        }
    }
}

class UserInput(val userName: String) : BaseInput
