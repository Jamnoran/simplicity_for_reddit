package com.simplicity.simplicityaclientforreddit.main.screen.test

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.MarkDownText
import com.simplicity.simplicityaclientforreddit.main.theme.Background
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun TestScreen(navController: NavHostController, logic: TestLogic, state: UiState<String>) {
    when (state) {
        is UiState.Loading -> ScreenLoading(state.loadingMessage)
        is UiState.Error -> ScreenError()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, state.data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Show(navigator: NavHostController, data: String) {
    Log.i("TestScreen", "Showing screen!")
    Column(Modifier.fillMaxWidth().background(Background).verticalScroll(rememberScrollState())) {
        MarkDownText(body = data) {
            Log.i("TestScreen", "We got click on this url $it")
        }
//        ShowGif(it = "https://i.imgur.com/kbIohCJ.gifv")
    }
//    Column(Modifier.fillMaxWidth().background(Background)) {
//        CWebView(url = "https://consent.yahoo.com/v2/collectConsent?sessionId=3_cc-session_777246a8-9f31-461a-877b-12273d1e1552")
//    }

//    val scaffoldState = rememberScaffoldState()
//    Scaffold(
//        scaffoldState = scaffoldState,
//        content = { paddingValues ->
//            Column(
//                Modifier
//                    .padding(paddingValues)
//                    .fillMaxWidth()
//                    .background(Background)
//            ) {
//                CWebView(Modifier.height(800.dp), url = "https://www.bbc.com/news/science-environment-64052740")
//            }
//        },
//        bottomBar = { CText(modifier = Modifier.background(Color.Transparent), text = "Bottom App Bar") }
//    )
}

// @Composable
// fun MarkDownText(body: String, linkClicked: (String) -> Unit) {
//    val listOfMarkDowns = ArrayList<MarkDownInfo>()
//
//    // Do a rundown of all characters that's should be converted before markdown handling
//    var text = replaceBaseCharacters(body)
//    // Find all markdowns
//    for (style in listOf(BOLD, STRIKETHROUGH, ITALIC, LINK, HEADER_1)) {
//        text = markDown(style, listOfMarkDowns, text)
//    }
//
//    // Add all markdown styles
//    val annotatedLinkString: AnnotatedString = buildAnnotatedString {
//        pushStyle(style = SpanStyle(color = OnSurface))
//        append(text)
//        for (style in listOfMarkDowns) {
//            Log.i("TestScreen", "Adding style : ${style.type} to index : ${style.startIndex} - ${style.endIndex}")
//            addStyle(getStyleFromInfo(style), style.startIndex, style.endIndex)
//        }
//    }
//
//    ClickableText(
//        modifier = Modifier,
//        text = annotatedLinkString,
//        onClick = {
//            Log.i("MarkDownText", "LinkableText on position $it")
//            annotatedLinkString
//                .getStringAnnotations("URL", it, it)
//                .firstOrNull()?.let { markDownClickable ->
//                    Log.i("MarkDownText", "Open url: $markDownClickable")
//                    linkClicked.invoke(markDownClickable.item)
//                }
//        }
//    )
// }
//
// fun markDown(markDownType: MarkDownType, listOfMarkDowns: java.util.ArrayList<MarkDownInfo>, input: String): String {
//    var text = input
//    when (markDownType) {
//        BOLD_SECONDARY -> return text
//        ITALIC_SECONDARY -> return text
//        NONE -> return text
//        SKIP -> return text
//        else -> {}
//    }
//    val iterator = markDownType.regExp.findAll(text, 0).iterator()
//
//    // Add all markdowns in an array
//    iterator.forEachRemaining { markDownRegExpResult ->
//        val endIndex = markDownRegExpResult.range.last - (markDownType.preFix?.length ?: 1) + 1
//        listOfMarkDowns.add(MarkDownInfo(markDownType, markDownRegExpResult.range.first, endIndex))
//    }
//    // Remove all markdown characters in text making it presentable to user
//    for (style in listOfMarkDowns) {
//        val startPreFixIndex = style.startIndex
//        val endPrefixIndex = style.endIndex
//        style.type.preFix?.let { preFix ->
//            val countOfPrefixChars = preFix.length
//            val endIndex = startPreFixIndex + countOfPrefixChars - 1
//            for (i in startPreFixIndex..endIndex) {
//                text = text.replaceRange(i, i + 1, Char(0).toString())
//            }
//        }
//
//        if (!styleEndsWithNewLine(style.type)) {
//            style.type.postFix?.let { postFix ->
//                val countOfPrefixChars = postFix.length
//                val endIndex = endPrefixIndex + countOfPrefixChars - 1
//                for (i in endPrefixIndex..endIndex) {
//                    text = text.replaceRange(i, i + 1, Char(0).toString())
//                }
//            }
//        }
//        // Special case for hiding text for links
//        if (style.type == LINK) {
//            val subString = text.substring(style.startIndex, style.endIndex)
//            val indexOfEndOfDescription = subString.indexOf("]")
//            val indexInTextForEndOfDescription = style.startIndex + indexOfEndOfDescription + 1 // Fix this not working to remove first one
//            val endIndex = style.endIndex - 1
//            for (i in indexInTextForEndOfDescription..endIndex) {
//                text = text.replaceRange(i, i + 1, Char(0).toString())
//            }
//        }
//    }
//    return text
// }
//
// fun styleEndsWithNewLine(type: MarkDownType): Boolean {
//    return type.postFix.equals("\n")
// }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        val text =
            "#Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold** and *italic* text\n\n[Link with desc](https://www.google.se/)\n\n[https://www.svt.se/](https://www.svt.se/)\n\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\n\n~~Strikethrough~~ text \n\n`This is some inlined` code\n\n^(Superscript is this)\n\n&gt;!This is a spoiler!&lt;\n\n# Header in the middle of the text\n\n* Bullted list\n* with a couple of points\n\n1. Numbered list in a bulleted list\n\n&amp;#x200B;\n\n1. Simple numbered list\n\n&gt; This is a quote from the great emperor\n\n&amp;#x200B;\n\n    Code blocks looks like this\n\nImage:\n\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\n\n&amp;#x200B;\n\n&amp;#x200B;\n\n|Column 1|Column 2|Column 3|\n|:-|:-|:-|\n|Row 2|Row 2|Row 2|"
        Show(rememberNavController(), text)
    }
}
