package com.simplicity.simplicityaclientforreddit.main.screen.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun SearchScreen(navigator: NavHostController, logic: SearchLogic, state: UiState<List<String>>) {
    Log.i("SearchScreen", "State is being updated to -> $state")
    when (state) {
        is UiState.Loading -> ScreenLoading(state.loadingMessage)
        is UiState.Error -> ScreenError()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navigator, state.data, "", logic)
    }
}

@Composable
fun Show(navigator: NavHostController, listOfSubs: List<String>, query: String, logic: SearchLogic) {
    Log.i("SearchScreen", "Updating list with count : ${listOfSubs.count()}")
    DefaultScreen(modifier = Modifier) {
        Column {
            Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                BasicTextField(value = query, textStyle = TextStyle(color = OnBackground), onValueChange = {
                    Log.i("SearchScreen", "Input changed to : $it")
                    logic.updatedInput(it)
                })
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { logic.getNsfwList() }) {
                    CText(text = "NSFW", color = OnBackground)
                }
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(content = {
                items(listOfSubs) {
                    CText(
                        Modifier.padding(8.dp).clickable { navigator.navigate(NavRoute.POSTS_LIST.withArgs(it)) },
                        text = "r/$it",
                        color = OnBackground
                    )
                    Divider()
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), listOf("Testing"), "", SearchLogic())
    }
}
