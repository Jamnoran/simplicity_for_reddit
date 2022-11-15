package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.components.buttons.CToggleButton
import com.simplicity.simplicityaclientforreddit.main.components.buttons.TextButton
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.texts.OnSurfaceText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.external.posts.RedditPost
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditCommentListener
import com.simplicity.simplicityaclientforreddit.main.screen.posts.RedditPostListener
import java.text.NumberFormat

@Composable
fun PostFooter(post: RedditPost, listener: RedditPostListener) {
    val context = LocalContext.current
    Row(
        Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var ownVote = 0
        // Upvote
        CToggleButton(isChecked = false, onClick = {
            listener.upVote.invoke(post)
            if (ownVote == 0) ownVote++
        }, disabledIcon = R.drawable.up_arrow_disabled, enabledIcon = R.drawable.up_arrow_clicked)
        Spacer(Modifier.width(4.dp))
        val descriptiveNumber = NumberFormat.getInstance().format((post.data.score + ownVote))
        OnSurfaceText(text = descriptiveNumber)
        Spacer(Modifier.width(4.dp))
        // DowVote
        CToggleButton(isChecked = false, onClick = {
            listener.downVote.invoke(post)
            if (ownVote == 0) ownVote--
        }, disabledIcon = R.drawable.down_arrow_disabled, enabledIcon = R.drawable.down_arrow_clicked)
        // Comments
        Spacer(Modifier.width(4.dp))
        Comments(post, listener)
        Spacer(Modifier.weight(1f))
        // Share
        TextButton(text = "Share") { listener.shareClick.invoke(post) }
        Spacer(Modifier.width(8.dp))
        // Hide
        TextButton(text = "Hide") {
            Toast.makeText(context, "Hiding sub ${post.data.subredditPrefixed}", Toast.LENGTH_LONG).show()
            listener.hideSubClick.invoke(post)
        }
        Spacer(Modifier.width(8.dp))
        // Go to reddit
        TextButton(text = "Red") { listener.redditClick.invoke(post) }
    }
}

@Composable
fun CommentFooter(comment: ChildrenData, listener: RedditCommentListener) {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var ownVote = 0
        // Scores
        CToggleButton(isChecked = false, onClick = {
            listener.upVote.invoke(comment)
            if (ownVote == 0) ownVote++
        }, disabledIcon = R.drawable.up_arrow_disabled, enabledIcon = R.drawable.up_arrow_clicked)
        Spacer(Modifier.width(8.dp))
        val descriptiveNumber = NumberFormat.getInstance().format((comment.score.plus(ownVote)))
        OnSurfaceText(text = descriptiveNumber)
        Spacer(Modifier.width(8.dp))
        CToggleButton(isChecked = false, onClick = {
            listener.downVote.invoke(comment)
            if (ownVote == 0) ownVote--
        }, disabledIcon = R.drawable.down_arrow_disabled, enabledIcon = R.drawable.down_arrow_clicked)
        // Comments
        Spacer(Modifier.weight(1f))
        // Share
        TextButton(text = "Share") { listener.shareClick.invoke(comment) }
        Spacer(Modifier.width(8.dp))
        // Go to reddit
        TextButton(text = "Red") { listener.redditClick.invoke(comment) }
    }
}

@Composable
fun Comments(post: RedditPost, listener: RedditPostListener) {
    Row(Modifier.clickable { listener.readComments }, verticalAlignment = Alignment.CenterVertically) {
        CImage(iconResource = R.drawable.chat_icon)
        Spacer(Modifier.width(4.dp))
        val numberOfComments = NumberFormat.getInstance().format((post.data.numComments))
        TextButton(text = numberOfComments) { listener.readComments.invoke(post) }
    }
}

@Preview
@Composable
fun FooterPreview() {
    PostFooter(post = TesterHelper.getPost(), listener = RedditPostListener.preview())
}

@Preview
@Composable
fun FooterCommentPreview() {
    CommentFooter(comment = TesterHelper.getChildrenData(), listener = RedditCommentListener.preview())
}
