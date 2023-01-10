package com.simplicity.simplicityaclientforreddit.main.utils.markdown

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.simplicity.simplicityaclientforreddit.main.theme.LIGHT_BROWN
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.Secondary
import com.simplicity.simplicityaclientforreddit.main.theme.Transparent
import com.simplicity.simplicityaclientforreddit.main.theme.Typography
import com.simplicity.simplicityaclientforreddit.main.theme.Typography.Companion.SMALL_TEXT_SIZE

data class MarkDownInfo(val type: MarkDownType, val startIndex: Int, val endIndex: Int, val extra: String? = null) {
    fun getStyleFromInfo(): SpanStyle {
        var fontWeight = FontWeight.Normal
        var fontStyle = FontStyle.Normal
        var fontColor = OnSurface
        var textDecoration = TextDecoration.None
        var fontSize = Typography.DEFAULT_TEXT_SIZE
        var backgroundColor = Transparent
        when (type) {
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
                fontColor = Secondary
                textDecoration = TextDecoration.Underline
            }
            MarkDownType.NONE,
            MarkDownType.SKIP -> {
            }
            MarkDownType.HEADER_1 -> fontSize = Typography.HEADER_4_TEXT_SIZE
            MarkDownType.INLINE_CODE -> {
                fontColor = Primary
                backgroundColor = LIGHT_BROWN
            }
            MarkDownType.SUPERSCRIPT -> {
                fontSize = SMALL_TEXT_SIZE
                fontColor = Primary
            }
        }
        return SpanStyle(
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            fontSize = fontSize,
            textDecoration = textDecoration,
            color = fontColor,
            background = backgroundColor
        )
    }
}

enum class MarkDownType(val preFix: String?, val postFix: String?, val regExp: Regex) {
    BOLD("**", "**", "(\\*\\*)(.*?)(\\*\\*)".toRegex()),
    BOLD_SECONDARY("__", "__", "(__)(.*?)(__)".toRegex()),
    ITALIC("*", "*", "(\\*)(.*?)(\\*)".toRegex()),
    ITALIC_SECONDARY("_", "_", "(_)(.*?)(_)".toRegex()),
    INLINE_CODE("`", "`", "(`)(.*?)(`)".toRegex()),
    SUPERSCRIPT("^(", ")", "(\\^\\()(.*?)(\\))".toRegex()),
    STRIKETHROUGH("~~", "~~", "(~~)(.*?)(~~)".toRegex()),
    HEADER_1("#", "\n", "(#)(.*?)(\n)".toRegex()), // ^#.*
    LINK("[", ")", "\\[(.*?)]\\((.*?)\\)".toRegex()),
    URL(
        "",
        null,
        """(?i)\b((?:https?://|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))""".toRegex()
    ),
    NONE(null, null, "".toRegex()),
    SKIP(null, null, "".toRegex())
}

fun startsWith(restOfString: String, markDownType: MarkDownType): Boolean {
    return restOfString.startsWith(markDownType.preFix.toString())
}

fun getPostFixPosition(index: Int, text: String, markDownType: MarkDownType): Int {
    val restOfString = text.substring(index)
    val textAfterPreFix = restOfString.substring(markDownType.preFix!!.length)
    val posOfPostFix = textAfterPreFix.indexOf(markDownType.postFix!!)
    return index + markDownType.preFix.length + posOfPostFix + markDownType.postFix.length + 1
}
