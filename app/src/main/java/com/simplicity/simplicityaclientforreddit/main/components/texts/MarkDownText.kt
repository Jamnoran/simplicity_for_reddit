package com.simplicity.simplicityaclientforreddit.main.components.texts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.models.internal.MarkDownSigns
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.theme.Typography.Companion.DEFAULT_TEXT_SIZE
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownInfo
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType

class Link(val linkText: String, val externalUrl: String? = null, val onClick: (String) -> Unit)

@Composable
fun MarkDownSimple(
    modifier: Modifier = Modifier,
    body: String,
    style: SpanStyle = SpanStyle(color = OnSurface, fontSize = DEFAULT_TEXT_SIZE)
) {
    val listOfMarkDowns = ArrayList<MarkDownInfo>()

    // Do a rundown of all characters that's should be converted before markdown handling
    var text = replaceBaseCharacters(body)
    // Find all markdowns
    for (markDownStyle in listOf(
        MarkDownType.BOLD,
        MarkDownType.STRIKETHROUGH,
        MarkDownType.ITALIC
    )) {
        text = findMarkDownStyle(markDownStyle, listOfMarkDowns, text)
    }

    // Replace all encapsulated characters
    text = replaceEncapsulatedCharacters(text)

    // Add all markdown styles
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = style)
        append(text)
        for (style in listOfMarkDowns) {
            addStyle(style.getStyleFromInfo(), style.startIndex, style.endIndex)
        }
    }
    Text(modifier = modifier, text = annotatedLinkString)
}

@Composable
fun MarkDownText(modifier: Modifier = Modifier, body: String, linkClicked: (String) -> Unit) {
    val markDownStyles = ArrayList<MarkDownInfo>()

    // Do a rundown of all characters that's should be converted before markdown handling
    var text = replaceBaseCharacters(body)
    // Find all markdowns
    for (markDownType in listOf(
        MarkDownType.BOLD,
        MarkDownType.BOLD_SECONDARY,
        MarkDownType.STRIKETHROUGH,
        MarkDownType.ITALIC,
        MarkDownType.ITALIC_SECONDARY,
        MarkDownType.INLINE_CODE,
        MarkDownType.SUPERSCRIPT,
        MarkDownType.LINK,
        MarkDownType.HEADER_1,
        MarkDownType.URL
    )) {
        text = findMarkDownStyle(markDownType, markDownStyles, text)
    }

    // Replace all encapsulated characters
    text = replaceEncapsulatedCharacters(text)

    // Add all markdown styles
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
        pushStyle(style = SpanStyle(color = OnSurface, fontSize = DEFAULT_TEXT_SIZE))
        append(text)
        for (style in markDownStyles) {
            addStyle(style.getStyleFromInfo(), style.startIndex, style.endIndex)
            // Add link annotation
            if (style.type == MarkDownType.LINK || style.type == MarkDownType.URL) {
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

fun findMarkDownStyle(
    markDownType: MarkDownType,
    markDownStyles: ArrayList<MarkDownInfo>,
    input: String
): String {
    var text = input
    when (markDownType) {
        MarkDownType.NONE -> return text
        MarkDownType.SKIP -> return text
        else -> {}
    }
    val iterator = markDownType.regExp.findAll(text, 0).iterator()

    // Add all markdowns in an array
    iterator.forEachRemaining { markDownRegExpResult ->
        val endIndex = markDownRegExpResult.range.last - (markDownType.preFix?.length ?: 1) + 1
        val markDownExtra = getMarkDownExtra(markDownType, markDownRegExpResult)
        markDownStyles.add(
            MarkDownInfo(
                markDownType,
                markDownRegExpResult.range.first,
                endIndex,
                markDownExtra
            )
        )
    }
    // Remove all markdown characters in text making it presentable to user
    for (style in markDownStyles) {
        if (style.type == markDownType) {
            val startPreFixIndex = style.startIndex
            val endPrefixIndex = style.endIndex
            // Remove PRE_FIX
            style.type.preFix?.let { preFix ->
                val countOfPrefixChars = preFix.length
                val endIndex = startPreFixIndex + countOfPrefixChars - 1
                for (i in startPreFixIndex..endIndex) {
                    text = text.replaceRange(i, i + 1, Char(0).toString())
                }
            }

            // Remove POST_FIX
            if (!styleEndsWithNewLine(style.type)) {
                style.type.postFix?.let { postFix ->
                    val countOfPrefixChars = postFix.length
                    val endIndex = endPrefixIndex + countOfPrefixChars - 1
                    for (i in endPrefixIndex..endIndex) {
                        text = text.replaceRange(i, i + 1, Char(0).toString())
                    }
                }
            }

            // SPECIALS
            if (style.type == MarkDownType.LINK) {
                // Special case for hiding text for links
                text = replaceLinkDataFromText(text, style)
            }
        }
    }
    return text
}

fun replaceLinkDataFromText(text: String, style: MarkDownInfo): String {
    val subString = text.substring(style.startIndex, style.endIndex)
    val indexOfEndOfDescription = subString.indexOf("]")
    val startPositionToRemove = style.startIndex + indexOfEndOfDescription
    val endPositionToRemove = style.endIndex
    var replaceText: String? = Char(0).toString()
    for (i in 1..endPositionToRemove - startPositionToRemove) {
        replaceText = replaceText.plus(Char(0).toString())
    }
    return text.replaceRange(startPositionToRemove, (endPositionToRemove + 1), replacement = replaceText.toString())
}

fun getMarkDownExtra(markDownType: MarkDownType, markDownRegExpResult: MatchResult): String? {
    return when (markDownType) {
        MarkDownType.LINK -> {
            markDownRegExpResult.value.substring(
                (markDownRegExpResult.value.indexOf("(") + 1),
                markDownRegExpResult.value.indexOf(")")
            )
        }
        MarkDownType.URL -> {
            markDownRegExpResult.value
        }
        else -> {
            null
        }
    }
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
//        MarkDownText(Modifier.fillMaxSize(), body = sampleData) {}

        MarkDownSimple(
            body = "This is a simple markdown",
            style = SpanStyle(fontWeight = FontWeight.Bold, color = OnSurface)
        )
    }
}
