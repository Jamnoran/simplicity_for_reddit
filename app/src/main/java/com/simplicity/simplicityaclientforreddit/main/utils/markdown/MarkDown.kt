package com.simplicity.simplicityaclientforreddit.main.utils.markdown

import android.util.Log
import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.models.internal.data.MarkDownLink
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.BOLD
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.BOLD_SECONDARY
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.HEADER_1
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.ITALIC
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.ITALIC_SECONDARY
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.NONE
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.SKIP
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.STRIKETHROUGH
val linkDescriptionRegexp = "^\\[.{0,99}\\]".toRegex()
val linkRegexp = "\\(.{0,99}\\)".toRegex()

data class MarkDownData(val type: MarkDownType, val skipToIndex: Int, val charToAdd: CharSequence?, val removeAtIndex: Int)

enum class MarkDownType(val preFix: String?, val postFix: String?) {
    BOLD("**", "**"),
    BOLD_SECONDARY("__", "__"),
    ITALIC("*", "*"),
    ITALIC_SECONDARY("_", "_"),
    STRIKETHROUGH("~~", "~~"),
    HEADER_1("#", "\n"),
    LINK(null, null),
    NONE(null, null),
    SKIP(null, null)
}

fun getMarkDown(listOfMarkDownsAlready: ArrayList<MarkDownData>, body: String, index: Int): MarkDownData {
    val restOfString = body.substring(index)
    val arrayOfMarkDowns = listOf(BOLD, BOLD_SECONDARY, ITALIC_SECONDARY, ITALIC, STRIKETHROUGH, HEADER_1)

    for (markDown in arrayOfMarkDowns) {
        val startsWithAlreadyAdded = startsWithAdded(listOfMarkDownsAlready, markDown, restOfString)
        if (startsWithAlreadyAdded) {
            return MarkDownData(SKIP, index + (markDown.postFix?.length ?: 0), body.subSequence(index, index + 1), -1)
        }
        if (startsWith(restOfString, markDown)) {
            val postFixPosition = getPostFixPosition(index, body, markDown)
            return MarkDownData(markDown, index + markDown.preFix!!.length, null, postFixPosition)
        }
    }
    // Check for link
    if (linkDescriptionRegexp.containsMatchIn(restOfString)) {
        return getLinkMarkDown(restOfString, index)
    }
    return MarkDownData(NONE, -1, body.subSequence(index, index + 1), -1)
}

fun getLinkMarkDown(restOfString: String, index: Int): MarkDownData {
    var link = ""
    var linkDescription = ""
    linkDescriptionRegexp.find(restOfString, 0)?.value?.let { linkDescWithHooks ->
        linkDescription = linkDescWithHooks.substring(1, linkDescWithHooks.length - 1)
    }
    linkRegexp.find(restOfString, 0)?.value?.let { linkWithParentheses ->
        link = linkWithParentheses.substring(1, linkWithParentheses.length - 1)
    }
    val jsonOfMarkDownLink = Gson().toJson(MarkDownLink(linkDescription, link))
    val removeAtIndex = (index + linkDescription.length + 1)
    val skipToIndex = (index + restOfString.indexOf(")") + 1)
    Log.i("MarkDown", "We got this link : $jsonOfMarkDownLink with endOfMarkDownText $removeAtIndex and endIndex $skipToIndex")
    return MarkDownData(MarkDownType.LINK, skipToIndex, jsonOfMarkDownLink, removeAtIndex)
}

fun startsWithAdded(listOfMarkDownsAlready: ArrayList<MarkDownData>, markDownType: MarkDownType, restOfString: String): Boolean {
    val markDownAdded = listOfMarkDownsAlready.find { it.type == markDownType }
    markDownAdded?.let {
        if (restOfString.startsWith(markDownType.preFix.toString())) {
            return true
        }
    }
    return false
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
