package com.simplicity.simplicityaclientforreddit.main.media

import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Children
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponseData
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Replies
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.RepliesData
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.Subreddit
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.user.User
import com.simplicity.simplicityaclientforreddit.main.models.internal.HiddenSubs

class TesterHelper {

    companion object {
        fun getPost(): RedditPost {
            return RedditPost(
                data = RedditPost.Data(
                    id = "a1231f",
                    name = "Name of post",
                    title = "Title of post",
                    author = "MisterPoster",
                    subreddit = "subreddit",
                    is_gallery = false,
                    is_video = false,
                    preview = null,
                    galleryData = null,
                    media = null,
                    selftext = "This is a selftext",
                    created = 1667757291805,
                    mediaMetadata = null,
                    secureMediaEmbed = null,
                    postHint = "image",
                    tournament_data = null,
                    numComments = 1234,
                    ups = 100,
                    downs = 12,
                    score = 98172,
                    crosspostParentList = null
                )
            )
        }

        fun getHiddenSub(): HiddenSubs {
            return HiddenSubs(
                123,
                "boring_sub"
            )
        }

        fun getChildrenData(body: String? = "This is a comment", author: String? = "Bosse"): ChildrenData {
            return ChildrenData(body = body, author = author, createdUtc = 1667757291805)
        }

        fun getComments(): CommentResponse {
            val replyReplyChildrenData = ChildrenData(body = "This is a reply to a reply", author = "Berit", createdUtc = 1667757291805)
            val replyReplyChildren = arrayListOf(
                Children(kind = "t3", childrenData = replyReplyChildrenData),
                Children(kind = "t3", childrenData = replyReplyChildrenData)
            )
            val replyChildrenData = ChildrenData(body = "This is a reply", author = "Göran", createdUtc = 1667757291805)
            replyChildrenData.repliesCustomParsed = Replies(kind = "t3", RepliesData(children = replyReplyChildren))
            val replyChildren = arrayListOf(
                Children(kind = "t3", childrenData = replyChildrenData),
                Children(kind = "t3", childrenData = replyChildrenData)
            )
            val childrenData = getChildrenData()
            val children =
                arrayListOf(Children(kind = "t3", childrenData = childrenData), Children(kind = "t3", childrenData = childrenData))
            childrenData.repliesCustomParsed = Replies("t3", RepliesData(children = replyChildren))
            val commentResponseData = CommentResponseData(after = "", before = "", children = children)
            return CommentResponse("t1", commentResponseData, getPost())
        }

        fun getUser(): User {
            return User(
                name = "Bosse",
                created = 1667757291805,
                bannerImg = "http://www.google.com",
                totalKarma = 1234,
                subreddit = Subreddit(
                    publicDescription = "This is a users detail pages public description"
                )
            )
        }

        fun subRedditVisited(): List<String> {
            return listOf(
                "aww",
                "aww",
                "aww",
                "aww",
                "asdf",
                "aww",
                "aww",
                "aww",
                "wyretwer",
                "aww",
                "afdgsdf",
                "aww",
                "aww",
                "asdgag",
                "aww",
                "aww",
                "aww",
                "xvbcc",
                "aww",
                "aww",
                "aww",
                "liasjfdlaksjdflakjsdflkajsdflkajfdslkajdsflkajfdslkajsf",
                "aww",
                "coolstuff"
            )
        }
    }
}
