package com.simplicity.simplicityaclientforreddit.main.io.retrofit.serializers

import com.google.gson.*
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Children
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.CommentResponse
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.Replies
import java.lang.reflect.Type

class CommentSerializer : JsonDeserializer<CommentResponse?> {
    private val TAG: String = "CommentSerializer"

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CommentResponse? {
        var commentResponse = Gson().fromJson(json, CommentResponse::class.java)
        var isComment = false
        commentResponse.commentResponseData?.children?.get(0)?.let {
            if (it.kind.equals("t1")) {
                isComment = true
            }
        }
        commentResponse = if (isComment) {
            addRepliesToCommentObject(commentResponse, json)
        } else {
            addPostObject(commentResponse, json)
        }
        return commentResponse
    }

    private fun addRepliesToCommentObject(commentResponse: CommentResponse, json: JsonElement): CommentResponse? {
        val commentResponseJsonObject = json.asJsonObject
        val data = commentResponseJsonObject["data"].asJsonObject
        val children = data["children"].asJsonArray

        children.forEachIndexed { index, child ->
            val childFromResponse = commentResponse.commentResponseData?.children?.get(index)
            mapToRepliesObject(childFromResponse, child)
        }
        return commentResponse
    }

    private fun addPostObject(commentResponse: CommentResponse, json: JsonElement): CommentResponse? {
        val commentResponseJsonObject = json.asJsonObject
        val data = commentResponseJsonObject["data"].asJsonObject
        val children = data["children"].asJsonArray

        children.forEachIndexed { _, child ->
            val redditPostObject = Gson().fromJson(child, RedditPost::class.java)
            commentResponse.redditPost = redditPostObject
        }
        return commentResponse
    }

    private fun addRepliesToRepliesObject(repliesObject: Replies, repliesJsonObject: JsonObject) {
        val data = repliesJsonObject["data"].asJsonObject
        val children = data["children"].asJsonArray
        children.forEachIndexed { index, child ->
            val childFromResponse = repliesObject.repliesData?.children?.get(index)
            mapToRepliesObject(childFromResponse, child)
        }
    }

    private fun mapToRepliesObject(childFromResponse: Children?, child: JsonElement) {
        val childData = child.asJsonObject["data"].asJsonObject
        var replies: JsonObject? = null
        try {
            replies = childData["replies"].asJsonObject
        } catch (e: Exception) {
        }
        replies?.let {
//            Log.i(TAG, "repliesObject: $replies")
            // Found child that has replies.
            val repliesObject = Gson().fromJson(replies, Replies::class.java)
            repliesObject.repliesData?.children
            addRepliesToRepliesObject(repliesObject, it)
//            Log.i(TAG, "SuccessFull Gson parsed replies : $repliesObject")
            childFromResponse?.childrenData?.repliesCustomParsed = repliesObject
        }
    }
}
