package com.simplicity.simplicityaclientforreddit.main.screen.test

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.images.CImageZoomable
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.MarkDownText
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun TestScreen(navController: NavHostController, logic: TestLogic, state: UiState<String>) {
    DefaultScreen(onStop = { logic.onPause() }) {
        when (state) {
            is UiState.Loading -> ScreenLoading(state.loadingMessage)
            is UiState.Error -> ScreenError()
            is UiState.Empty -> {}
            is UiState.Success -> Show(navController, state.data)
        }
    }
}

@Composable
fun Show(navigator: NavHostController, data: String) {
    Log.i("TestScreen", "Showing screen!")
    DefaultScreen {
        CImageZoomable(modifier = Modifier.fillMaxSize(), url = "https://images.pexels.com/photos/235986/pexels-photo-235986.jpeg?auto=compress&cs=tinysrgb&w=600")
//        MarkDownText(body = data) {
//            Log.i("TestScreen", "We got click on this url $it")
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        val text =
            "#Heading\n\n**This is bold text**\n\n*Italic text*\n\n**Mixed bold** and *italic* text\n\n[Link with desc](https://www.google.se/)\n\n[https://www.svt.se/](https://www.svt.se/)\n\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\n\n~~Strikethrough~~ text \n\n`This is some inlined` code\n\n^(Superscript is this)\n\n&gt;!This is a spoiler!&lt;\n\n# Header in the middle of the text\n\n* Bullted list\n* with a couple of points\n\n1. Numbered list in a bulleted list\n\n&amp;#x200B;\n\n1. Simple numbered list\n\n&gt; This is a quote from the great emperor\n\n&amp;#x200B;\n\n    Code blocks looks like this\n\nImage:\n\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\n\n&amp;#x200B;\n\n&amp;#x200B;\n\n|Column 1|Column 2|Column 3|\n|:-|:-|:-|\n|Row 2|Row 2|Row 2|"
        Show(rememberNavController(), text)
    }
}
