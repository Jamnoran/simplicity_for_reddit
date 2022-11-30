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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.models.internal.MarkDownSigns
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownData
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.getMarkDown

class Link(val linkText: String, val externalUrl: String? = null, val onClick: (String) -> Unit)

@Composable
fun MarkDownText(modifier: Modifier, body: String) {
    // # Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold**
    val bodyAfterPreReplacing = replaceBaseCharacters(body)

    var pauseAppendUntilIndex = -1
    val listOfMarkDowns = ArrayList<MarkDownData>()
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = SpanStyle(color = OnSurface))
        for (index in bodyAfterPreReplacing.indices) {
            listOfMarkDowns.removeAll { it.removeAtIndex == index }
            if (pauseAppendUntilIndex == index) {
                pauseAppendUntilIndex = -1
            }
            if (pauseAppendUntilIndex == -1) {
                val markDownType: MarkDownData = getMarkDown(listOfMarkDowns, bodyAfterPreReplacing, index)
                markDownType.let { type ->
                    pauseAppendUntilIndex = type.skipToIndex
                    if (type.type != MarkDownType.NONE && type.type != MarkDownType.SKIP) listOfMarkDowns.add(
                        type
                    )
                    if (type.type != MarkDownType.SKIP) {
                        type.charToAdd?.let { char ->
                            withStyle(getStyle(listOfMarkDowns)) {
                                append(char)
                            }
                        }
                    }
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

fun replaceBaseCharacters(body: String): String {
    var updatedBody = body.replace(MarkDownSigns.CHAR_LINE, MarkDownSigns.CHAR_LINE_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_AND, MarkDownSigns.CHAR_AND_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_QUOTE, MarkDownSigns.CHAR_QUOTE_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_GT, MarkDownSigns.CHAR_GT_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_LT, MarkDownSigns.CHAR_LT_VALUE)
    return updatedBody
}

fun getStyle(listOfMarkDowns: java.util.ArrayList<MarkDownData>): SpanStyle {
    var fontWeight = FontWeight.Normal
    var fontStyle = FontStyle.Normal
    for (markDown in listOfMarkDowns) {
        when (markDown.type) {
            MarkDownType.BOLD -> fontWeight = FontWeight.Bold
            MarkDownType.ITALIC,
            MarkDownType.ITALIC_SECONDARY -> {
                fontStyle = FontStyle.Italic
            }
            MarkDownType.NONE,
            MarkDownType.SKIP -> {
            }
        }
    }
    return SpanStyle(
        fontWeight = fontWeight,
        fontStyle = fontStyle
    )
}

@Composable
fun LinkableText(modifier: Modifier, body: String) {
    // # Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold**
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = SpanStyle(color = OnSurface))
        var pauseAppendUntilIndex = -1
        for (index in body.indices) {
            if (pauseAppendUntilIndex == -1) {
                pauseAppendUntilIndex = markDown(body, index, this)
                if (pauseAppendUntilIndex != -1) {
                    Log.i(
                        "LinkableText",
                        "Found markdown with start index[$index] and end[$pauseAppendUntilIndex]"
                    )
                }
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
//        val sampleData = "**Bold**, *italic*, `code`, [link](http://redditpreview.com), ~~strikethrough~~\n>Quote\n\n\n# Header 1\n## Header 2\n### Header 3\n"
//        val sampleData = "# Heading\\n\\n**This is bold text**\\n\\n*Italic text*\\n\\n**Mixed bold** and *italic* text\\n\\n[Link with desc](https://www.svt.se/)\\n\\n[https://www.svt.se/](https://www.svt.se/)\\n\\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\\n\\n~~Strikethrough~~ text \\n\\n`This is some inlined` code\\n\\n^(Superscript is this)\\n\\n&gt;!This is a spoiler!&lt;\\n\\n# Header in the middle of the text\\n\\n* Bullted list\\n* with a couple of points\\n\\n1. Numbered list in a bulleted list\\n\\n&amp;#x200B;\\n\\n1. Simple numbered list\\n\\n&gt;This is a quote from the great emperor\\n\\n&amp;#x200B;\\n\\n    Code blocks looks like this\\n\\nImage:\\n\\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\\n\\n&amp;#x200B;\\n\\n&amp;#x200B;\\n\\n|Column 1|Column 2|Column 3|\\n|:-|:-|:-|\\n|Row 2|Row 2|Row 2|"
        val sampleData =
            "# Heading\\n\\n**This is bold text**\\n\\n*Italic text*\\n\\n**Mixed bold** and *italic*"
        LinkableText(modifier = Modifier, body = sampleData)
    }
}
