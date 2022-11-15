package com.simplicity.simplicityaclientforreddit.main.components.texts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface

class Link(val linkText: String, val externalUrl: String? = null, val onClick: (String) -> Unit)

@Composable
fun LinkableText(modifier: Modifier, body: String) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = SpanStyle(color = OnSurface))
        var pauseAppendUntilIndex = -1
        for (index in body.indices) {
            if (pauseAppendUntilIndex == -1) {
                pauseAppendUntilIndex = markDown(body, index, this)
            } else {
                if (pauseAppendUntilIndex == index) {
                    pauseAppendUntilIndex = -1
                }
            }
        }
    }

    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        onClick = {
            Log.i("MarkDownText", "LinkableText on position $it")
        }
    )
}

fun markDown(body: String, index: Int, builder: AnnotatedString.Builder): Int {
    val markDownStart = "**"
    val markDownEnd = "**"
    val restText = body.substring(index)
    if (restText.startsWith(markDownStart)) {
        val restOfStringAfterPrefix = restText.substring(markDownStart.length)
        val indexOfEnd = restOfStringAfterPrefix.indexOf(markDownEnd)
        builder.append(restOfStringAfterPrefix.substring(0, indexOfEnd))
        builder.addStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold
            ),
            start = index,
            end = indexOfEnd
        )
        return index + indexOfEnd + markDownEnd.length + 1
    }
    builder.append(body[index])
    return -1
}

@Preview
@Composable
fun PreviewMarkDown() {
    Column(Modifier.fillMaxWidth()) {
        val sampleData = "**Bold**, *italic*, `code`, [link](http://redditpreview.com), ~~strikethrough~~\n>Quote\n\n\n# Header 1\n## Header 2\n### Header 3\n"
        LinkableText(modifier = Modifier, body = sampleData)
    }
}
