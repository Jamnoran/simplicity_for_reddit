package com.simplicity.simplicityaclientforreddit.main.screen.posts

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData

class RedditPostListener(
    val downVote: (post: RedditPost) -> Unit,
    val upVote: (post: RedditPost) -> Unit,
    val clearVote: (post: RedditPost) -> Unit,
    val authorClick: (post: RedditPost) -> Unit,
    val shareClick: (post: RedditPost) -> Unit,
    val hideSubClick: (post: RedditPost) -> Unit,
    val readComments: (post: RedditPost) -> Unit,
    val subredditClick: (post: RedditPost) -> Unit,
    val redditClick: (post: RedditPost) -> Unit,
    val linkClick: (post: RedditPost) -> Unit,
    val showError: (errorMessage: String) -> Unit,
    var postHidden: () -> Unit,
    var nextPost: () -> Unit
) {
    companion object {
        fun preview() = RedditPostListener({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}
class RedditCommentListener(
    val downVote: (post: ChildrenData) -> Unit,
    val upVote: (post: ChildrenData) -> Unit,
    val clearVote: (post: ChildrenData) -> Unit,
    val authorClick: (post: ChildrenData) -> Unit,
    val shareClick: (post: ChildrenData) -> Unit,
    val hideSubClick: (post: ChildrenData) -> Unit,
    val readComments: (post: ChildrenData) -> Unit,
    val subredditClick: (post: ChildrenData) -> Unit,
    val redditClick: (post: ChildrenData) -> Unit,
    val linkClick: (post: ChildrenData) -> Unit,
    val showError: (errorMessage: String) -> Unit,
    var nextPost: () -> Unit
) {
    companion object {
        fun preview() = RedditCommentListener({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}
