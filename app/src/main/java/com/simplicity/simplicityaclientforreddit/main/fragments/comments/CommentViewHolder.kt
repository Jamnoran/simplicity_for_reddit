package com.simplicity.simplicityaclientforreddit.main.fragments.comments

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewHolderItem
import com.simplicity.simplicityaclientforreddit.main.fragments.posts.list.util.RedditPostListenerImpl
import com.simplicity.simplicityaclientforreddit.main.models.external.responses.comments.ChildrenData
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetFormattedTextUseCase
import com.simplicity.simplicityaclientforreddit.main.usecases.text.GetTimeAgoUseCase
import java.text.NumberFormat

class CommentViewHolder(var wrapper: CommentViewData, val viewModel: CommentsViewModel, val author: String) : BaseViewHolderItem(wrapper) {
    var ownVote = 0

    override fun getLayout() = R.layout.comment_layout

    override fun bind(itemView: View) {
        view = itemView
        constructComment(wrapper.data.childrenData, view)
    }

    private fun constructComment(child: ChildrenData?, view: View) {
        child?.let {
            setAuthor(child, view)
            setComment(child, view)
            setReplies(child, view)
            setToggle(view)
            setUpBottomSection(child, view)
        }
    }

    private fun setAuthor(child: ChildrenData, itemView: View) {
        // Author name
        itemView.findViewById<TextView>(R.id.author)?.let { authorView ->
            child.author?.let { author ->
                authorView.visibility = View.VISIBLE
                string(R.string.comment_deleted)
                authorView.text = context()?.getString(R.string.comment_author, author)
            } ?: run {
                authorView.visibility = View.GONE
            }
        }
        // Is OP
        itemView.findViewById<TextView>(R.id.comment_op)?.let {
            if (child.author == author) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
        // Posted
        itemView.findViewById<TextView>(R.id.comment_posted)?.let {
            it.text = GetTimeAgoUseCase().execute(child.createdUtc)
        }
    }

    private fun setComment(child: ChildrenData, itemView: View) {
        itemView.findViewById<TextView>(R.id.comment)?.let { commentBodyView ->
            child.body?.let { body ->
                commentBodyView.setText(
                    GetFormattedTextUseCase(RedditPostListenerImpl(viewModel)).execute(body),
                    TextView.BufferType.SPANNABLE
                )
            } ?: run {
                commentBodyView.text = string(R.string.comment_deleted)
            }
        }
    }

    private fun setReplies(child: ChildrenData, itemView: View) {
        child.repliesCustomParsed?.repliesData?.let { replies ->
            for (reply in replies.children) {
                val layoutInflater = LayoutInflater.from(context())
                val commentLayout = layoutInflater.inflate(R.layout.comment_layout, null)
                itemView.findViewById<LinearLayout>(R.id.child_comments).addView(commentLayout)
                constructComment(reply.childrenData, commentLayout)
            }
        }
    }

    private fun setToggle(view: View) {
        view.setBackgroundColor(view.context.getColor(R.color.comment_background))
        view.setOnLongClickListener {
            toggleExpandView(view)
            true
        }
    }

    private fun setUpBottomSection(child: ChildrenData, itemView: View) {
        updateCommentVotes(child, itemView)

        itemView.findViewById<ImageView>(R.id.down_vote_button).setOnClickListener { downVote(child, itemView) }
        itemView.findViewById<ImageView>(R.id.up_vote_button).setOnClickListener { upVote(child, itemView) }

        itemView.findViewById<TextView>(R.id.hide_sub).visibility = View.GONE
        itemView.findViewById<ImageView>(R.id.share_button).visibility = View.GONE
        itemView.findViewById<ImageView>(R.id.chat_icon).visibility = View.GONE
        itemView.findViewById<TextView>(R.id.comments).visibility = View.GONE
    }

    private fun updateCommentVotes(
        child: ChildrenData,
        itemView: View
    ) {
        val votes = ((child.ups ?: 0) - (child.downs ?: 0)) + ownVote
        itemView.findViewById<TextView>(R.id.votes).text = NumberFormat.getInstance().format(votes)
    }

    private fun upVote(child: ChildrenData, itemView: View) {
        ownVote = +1
        updateButton(itemView.findViewById(R.id.up_vote_button), R.drawable.up_arrow_normal, R.color.post_vote_up_color)
        updateButton(itemView.findViewById(R.id.down_vote_button), R.drawable.down_arrow_disabled, R.color.post_vote_default_color)
        updateCommentVotes(child, itemView)
//        listener.voteUp(post)
    }

    private fun downVote(child: ChildrenData, itemView: View) {
        ownVote = -1
        updateButton(itemView.findViewById(R.id.down_vote_button), R.drawable.down_arrow_normal, R.color.post_vote_down_color)
        updateButton(itemView.findViewById(R.id.up_vote_button), R.drawable.up_arrow_disabled, R.color.post_vote_default_color)
        updateCommentVotes(child, itemView)
//        listener.voteDown(post)
    }

    private fun updateButton(imageView: ImageView, drawable: Int, tintColor: Int) {
        imageView.setImageResource(drawable)
        imageView.setColorFilter(ContextCompat.getColor(imageView.context, tintColor), android.graphics.PorterDuff.Mode.MULTIPLY)
    }

    private fun toggleExpandView(commentLayout: View) {
        commentLayout.findViewById<TextView>(R.id.comment)?.let { commentBodyView ->
            if (commentLayout.findViewById<LinearLayout>(R.id.child_comments).visibility == View.GONE) {
                commentLayout.setBackgroundColor(commentLayout.context.getColor(R.color.comment_background))
                commentBodyView.maxLines = 2048
                commentLayout.findViewById<LinearLayout>(R.id.child_comments).visibility = View.VISIBLE
            } else {
                commentLayout.setBackgroundColor(commentLayout.context.getColor(R.color.comment_background_minimized))
                commentBodyView.maxLines = 1
                commentLayout.findViewById<LinearLayout>(R.id.child_comments).visibility = View.GONE
            }
        }
    }
}
