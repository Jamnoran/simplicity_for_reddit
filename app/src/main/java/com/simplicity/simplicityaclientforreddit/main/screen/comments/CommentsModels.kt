package com.simplicity.simplicityaclientforreddit.main.screen.comments

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse

class CommentsInput(val postId: String, val subreddit: String) : BaseInput

data class Data(val response: CommentResponse) {
    companion object {
        fun preview(): Data {
            return Data(TesterHelper.getComments())
        }
    }
}
