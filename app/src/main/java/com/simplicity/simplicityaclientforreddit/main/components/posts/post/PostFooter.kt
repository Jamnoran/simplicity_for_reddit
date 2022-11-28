package com.simplicity.simplicityaclientforreddit.main.components.posts.post

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.components.buttons.CTextButton
import com.simplicity.simplicityaclientforreddit.main.components.buttons.CToggleButton
import com.simplicity.simplicityaclientforreddit.main.components.images.CImage
import com.simplicity.simplicityaclientforreddit.main.components.images.CImageButton
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var ownVote by remember { mutableStateOf(0) }
        Voting(post, listener, 0, ownVote = ownVote) { ownVote = it }
        // Comments
        Spacer(Modifier.width(4.dp))
        Comments(post, listener)
        Spacer(Modifier.weight(1f))
        // Share
        CImageButton(iconResource = android.R.drawable.ic_menu_share) { listener.shareClick.invoke(post) }
//        CTextButton(text = "Share") { listener.shareClick.invoke(post) }
        Spacer(Modifier.width(8.dp))
        // Hide
        CImageButton(iconResource = android.R.drawable.ic_menu_close_clear_cancel) {
            Toast.makeText(context, "Hiding sub ${post.data.subredditPrefixed}", Toast.LENGTH_LONG).show()
            listener.hideSubClick.invoke(post)
        }
//        CTextButton(text = "Hide") {
//            Toast.makeText(context, "Hiding sub ${post.data.subredditPrefixed}", Toast.LENGTH_LONG).show()
//            listener.hideSubClick.invoke(post)
//        }
        Spacer(Modifier.width(8.dp))
        // Go to reddit
//        CTextButton(text = "Red") { listener.redditClick.invoke(post) }
        CImageButton(iconResource = R.drawable.reddit_logo) { listener.redditClick.invoke(post) }
        Spacer(Modifier.width(8.dp))
    }
}

@Composable
fun Voting(post: RedditPost, listener: RedditPostListener, initialVoteValue: Int = 0, ownVote: Int = 0, onVote: (Int) -> Unit) {
//    var ownVote by remember { mutableStateOf(initialVoteValue) }
    // Upvote
    CToggleButton(isChecked = ownVote == 1, onClick = {
        onVote(if (it) 1 else 0)
//        ownVote = if (it) {
//            listener.upVote.invoke(post)
//            1
//        } else {
//            listener.clearVote.invoke(post)
//            0
//        }
    }, disabledIcon = R.drawable.up_arrow_disabled, enabledIcon = R.drawable.up_arrow_clicked)
//    Spacer(Modifier.width(4.dp))
    val descriptiveNumber = NumberFormat.getInstance().format((post.data.score + ownVote))
    OnSurfaceText(text = descriptiveNumber)
//    Spacer(Modifier.width(4.dp))
    // DownVote
    CToggleButton(isChecked = ownVote == -1, onClick = {
        onVote(if (it) -1 else 0)
//        ownVote = if (it) {
//            listener.downVote.invoke(post)
//            -1
//        } else {
//            listener.clearVote.invoke(post)
//            0
//        }
    }, disabledIcon = R.drawable.down_arrow_disabled, enabledIcon = R.drawable.down_arrow_clicked)
}

@Composable
fun CommentFooter(comment: ChildrenData, listener: RedditCommentListener) {
    Row(
        Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var ownVote by remember { mutableStateOf(0) } // Could check on the actual comment
        // Scores
        CToggleButton(isChecked = ownVote == 1, onClick = {
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
        CTextButton(text = "Share") { listener.shareClick.invoke(comment) }
        Spacer(Modifier.width(8.dp))
        // Go to reddit
        CTextButton(text = "Red") { listener.redditClick.invoke(comment) }
    }
}

@Composable
fun Comments(post: RedditPost, listener: RedditPostListener) {
    Row(Modifier.clickable { listener.readComments }, verticalAlignment = Alignment.CenterVertically) {
        CImage(iconResource = R.drawable.chat_icon)
        Spacer(Modifier.width(4.dp))
        val numberOfComments = NumberFormat.getInstance().format((post.data.numComments))
        CTextButton(text = numberOfComments) { listener.readComments.invoke(post) }
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
