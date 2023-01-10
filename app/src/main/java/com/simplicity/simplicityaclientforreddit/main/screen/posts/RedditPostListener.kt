package com.simplicity.simplicityaclientforreddit.main.screen.posts

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData

class RedditPostListener(
    val downVote: (post: RedditPost.Data) -> Unit,
    val upVote: (post: RedditPost.Data) -> Unit,
    val clearVote: (post: RedditPost.Data) -> Unit,
    val authorClick: (post: RedditPost.Data) -> Unit,
    val shareClick: (post: RedditPost.Data) -> Unit,
    val hideSubClick: (post: RedditPost.Data) -> Unit,
    val readComments: (post: RedditPost.Data) -> Unit,
    val subredditClick: (post: RedditPost.Data) -> Unit,
    val redditClick: (post: RedditPost.Data) -> Unit,
    val fullScreen: (post: RedditPost.Data) -> Unit,
    val linkClick: (post: RedditPost.Data) -> Unit,
    val linkUrlClick: (url: String) -> Unit,
    val linkExternalBrowserClick: (url: String) -> Unit,
    val showError: (errorMessage: String) -> Unit,
    var postHiddenFromView: () -> Unit,
    var postShownFromView: () -> Unit,
    var nextPost: () -> Unit
) {
    companion object {
        fun preview() = RedditPostListener({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}

class RedditCommentListener(
    val downVote: (post: ChildrenData) -> Unit,
    val upVote: (post: ChildrenData) -> Unit,
    val clearVote: (post: ChildrenData) -> Unit,
    val authorClick: (post: ChildrenData) -> Unit,
    val shareClick: (post: ChildrenData) -> Unit,
    val readComments: (post: ChildrenData) -> Unit,
    val subredditClick: (post: ChildrenData) -> Unit,
    val redditClick: (post: ChildrenData) -> Unit,
    val linkClick: (post: ChildrenData) -> Unit,
    val showError: (errorMessage: String) -> Unit,
    val linkExternalBrowserClick: (url: String) -> Unit
) {
    companion object {
        fun preview() = RedditCommentListener({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}
