package com.simplicity.simplicityaclientforreddit.main.components.texts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.models.internal.MarkDownSigns
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.Typography.Companion.DEFAULT_TEXT_SIZE
import com.simplicity.simplicityaclientforreddit.main.theme.Typography.Companion.HEADER_4_TEXT_SIZE
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownInfo
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType

class Link(val linkText: String, val externalUrl: String? = null, val onClick: (String) -> Unit)

@Composable
fun MarkDownText(modifier: Modifier = Modifier, body: String, linkClicked: (String) -> Unit) {
    val listOfMarkDowns = ArrayList<MarkDownInfo>()

    // Do a rundown of all characters that's should be converted before markdown handling
    var text = replaceBaseCharacters(body)
    // Find all markdowns
    for (style in listOf(MarkDownType.BOLD, MarkDownType.STRIKETHROUGH, MarkDownType.ITALIC, MarkDownType.LINK, MarkDownType.HEADER_1)) {
        text = markDown(style, listOfMarkDowns, text)
    }

    // Replace all encapsulated characters
    text = replaceEncapsulatedCharacters(text)

    // Add all markdown styles
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = SpanStyle(color = OnSurface, fontSize = DEFAULT_TEXT_SIZE))
        append(text)
        for (style in listOfMarkDowns) {
            addStyle(getStyleFromInfo(style), style.startIndex, style.endIndex)
            // Add link annotation
            if (style.type == MarkDownType.LINK) {
                addStringAnnotation(
                    tag = "URL",
                    annotation = style.extra ?: "",
                    start = style.startIndex,
                    end = style.endIndex
                )
            }
        }
    }

    // Add all the links possible (URL/USERS/SUBREDDITS)
    ClickableText(
        modifier = modifier,
        text = annotatedLinkString,
        onClick = {
            Log.i("MarkDownText", "Clicked on position $it")
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { markDownClickable ->
                    if (markDownClickable.item.isNotBlank()) {
                        Log.i("MarkDownText", "Open url: $markDownClickable")
                        linkClicked.invoke(markDownClickable.item)
                    } else {
                        Log.i("MarkDownText", "No url in this markdown")
                    }
                }
        }
    )
}

fun replaceEncapsulatedCharacters(text: String): String {
    var replacedText = text
    replacedText = replacedText.replace("\\[", "[")
    replacedText = replacedText.replace("\\]", "]")
    return replacedText
}

fun markDown(markDownType: MarkDownType, listOfMarkDowns: ArrayList<MarkDownInfo>, input: String): String {
    var text = input
    when (markDownType) {
        MarkDownType.BOLD_SECONDARY -> return text
        MarkDownType.ITALIC_SECONDARY -> return text
        MarkDownType.NONE -> return text
        MarkDownType.SKIP -> return text
        else -> {}
    }
    val iterator = markDownType.regExp.findAll(text, 0).iterator()

    // Add all markdowns in an array
    iterator.forEachRemaining { markDownRegExpResult ->
        val endIndex = markDownRegExpResult.range.last - (markDownType.preFix?.length ?: 1) + 1
        val markDownExtra = getMarkDownExtra(markDownType, markDownRegExpResult)
        listOfMarkDowns.add(MarkDownInfo(markDownType, markDownRegExpResult.range.first, endIndex, markDownExtra))
    }
    // Remove all markdown characters in text making it presentable to user
    for (style in listOfMarkDowns) {
        val startPreFixIndex = style.startIndex
        val endPrefixIndex = style.endIndex
        style.type.preFix?.let { preFix ->
            val countOfPrefixChars = preFix.length
            val endIndex = startPreFixIndex + countOfPrefixChars - 1
            for (i in startPreFixIndex..endIndex) {
                text = text.replaceRange(i, i + 1, Char(0).toString())
            }
        }

        if (!styleEndsWithNewLine(style.type)) {
            style.type.postFix?.let { postFix ->
                val countOfPrefixChars = postFix.length
                val endIndex = endPrefixIndex + countOfPrefixChars - 1
                for (i in endPrefixIndex..endIndex) {
                    text = text.replaceRange(i, i + 1, Char(0).toString())
                }
            }
        }
        // Special case for hiding text for links
        if (style.type == MarkDownType.LINK) {
            val subString = text.substring(style.startIndex, style.endIndex)
            val indexOfEndOfDescription = subString.indexOf("]")
            val indexInTextForEndOfDescription = style.startIndex + indexOfEndOfDescription + 1 // Fix this not working to remove first one
            val endIndex = style.endIndex - 1
            for (i in indexInTextForEndOfDescription..endIndex) {
                text = text.replaceRange(i, i + 1, Char(0).toString())
            }
        }
    }
    return text
}

fun getMarkDownExtra(markDownType: MarkDownType, markDownRegExpResult: MatchResult): String? {
    if (markDownType == MarkDownType.LINK) {
        return markDownRegExpResult.value.substring((markDownRegExpResult.value.indexOf("(") + 1), markDownRegExpResult.value.indexOf(")"))
    }
    return null
}

fun styleEndsWithNewLine(type: MarkDownType): Boolean {
    return type.postFix.equals("\n")
}

fun replaceBaseCharacters(body: String): String {
    var updatedBody = body.replace(MarkDownSigns.CHAR_LINE, MarkDownSigns.CHAR_LINE_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_AND, MarkDownSigns.CHAR_AND_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_QUOTE, MarkDownSigns.CHAR_QUOTE_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_GT, MarkDownSigns.CHAR_GT_VALUE)
    updatedBody = updatedBody.replace(MarkDownSigns.CHAR_LT, MarkDownSigns.CHAR_LT_VALUE)
    return updatedBody
}

fun getStyleFromInfo(markdown: MarkDownInfo): SpanStyle {
    var fontWeight = FontWeight.Normal
    var fontStyle = FontStyle.Normal
    var fontColor = OnBackground
    var textDecoration = TextDecoration.None
    var fontSize = DEFAULT_TEXT_SIZE
    when (markdown.type) {
        MarkDownType.BOLD,
        MarkDownType.BOLD_SECONDARY -> fontWeight = FontWeight.Bold
        MarkDownType.ITALIC,
        MarkDownType.ITALIC_SECONDARY -> {
            fontStyle = FontStyle.Italic
        }
        MarkDownType.STRIKETHROUGH -> {
            textDecoration = TextDecoration.LineThrough
        }
        MarkDownType.LINK,
        MarkDownType.URL -> {
            fontColor = Primary
            textDecoration = TextDecoration.Underline
        }
        MarkDownType.NONE,
        MarkDownType.SKIP -> {
        }
        MarkDownType.HEADER_1 -> fontSize = HEADER_4_TEXT_SIZE
    }
    return SpanStyle(
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        fontSize = fontSize,
        textDecoration = textDecoration,
        color = fontColor
    )
}

@Preview
@Composable
fun PreviewMarkDown() {
    Column(Modifier.fillMaxWidth()) {
        val sampleData =
            "#Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold** and *italic* text\n\n[Link with desc](https://www.svt.se/)\n\n[https://www.svt.se/](https://www.svt.se/)\n\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\n\n~~Strikethrough~~ text \n\n`This is some inlined` code\n\n^(Superscript is this)\n\n&gt;!This is a spoiler!&lt;\n\n# Header in the middle of the text\n\n* Bullted list\n* with a couple of points\n\n1. Numbered list in a bulleted list\n\n&amp;#x200B;\n\n1. Simple numbered list\n\n&gt; This is a quote from the great emperor\n\n&amp;#x200B;\n\n    Code blocks looks like this\n\nImage:\n\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\n\n&amp;#x200B;\n\n&amp;#x200B;\n\n|Column 1|Column 2|Column 3|\n|:-|:-|:-|\n|Row 2|Row 2|Row 2|"
//        val sampleData = "**Bold**, *italic*, `code`, [link](http://redditpreview.com), ~~strikethrough~~\n>Quote\n\n\n# Header 1\n## Header 2\n### Header 3\n"
//        val sampleData = "# Heading\\n\\n**This is bold text**\\n\\n*Italic text*\\n\\n**Mixed bold** and *italic* text\\n\\n[Link with desc](https://www.svt.se/)\\n\\n[https://www.svt.se/](https://www.svt.se/)\\n\\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\\n\\n~~Strikethrough~~ text \\n\\n`This is some inlined` code\\n\\n^(Superscript is this)\\n\\n&gt;!This is a spoiler!&lt;\\n\\n# Header in the middle of the text\\n\\n* Bullted list\\n* with a couple of points\\n\\n1. Numbered list in a bulleted list\\n\\n&amp;#x200B;\\n\\n1. Simple numbered list\\n\\n&gt;This is a quote from the great emperor\\n\\n&amp;#x200B;\\n\\n    Code blocks looks like this\\n\\nImage:\\n\\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\\n\\n&amp;#x200B;\\n\\n&amp;#x200B;\\n\\n|Column 1|Column 2|Column 3|\\n|:-|:-|:-|\\n|Row 2|Row 2|Row 2|"
//        val sampleData =
//            "# Heading\\n\\n**This is bold text**\\n\\n*Italic text*\\n\\n**Mixed bold** and *italic*"
        MarkDownText(Modifier.fillMaxSize(), body = sampleData) {}
    }
}
