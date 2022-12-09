package com.simplicity.simplicityaclientforreddit.main.utils.markdown

import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.BOLD
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.HEADER_1
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.ITALIC
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.ITALIC_SECONDARY
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.NONE
import com.simplicity.simplicityaclientforreddit.main.utils.markdown.MarkDownType.SKIP

data class MarkDownData(val type: MarkDownType, val skipToIndex: Int, val charToAdd: CharSequence?, val removeAtIndex: Int)

enum class MarkDownType(val preFix: String?, val postFix: String?) {
    BOLD("**", "**"),
    ITALIC("*", "*"),
    ITALIC_SECONDARY("_", "_"),
    HEADER_1("#", "\n"),
    NONE(null, null),
    SKIP(null, null)
}

fun getMarkDown(listOfMarkDownsAlready: ArrayList<MarkDownData>, body: String, index: Int): MarkDownData {
    val restOfString = body.substring(index)
    val arrayOfMarkDowns = listOf(BOLD, ITALIC_SECONDARY, ITALIC, HEADER_1)

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
    return MarkDownData(NONE, -1, body.subSequence(index, index + 1), -1)
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
    val postFixPosition = index + markDownType.preFix.length + posOfPostFix + markDownType.postFix.length + 1
    return postFixPosition
}
