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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.OnPrimary
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme
import com.simplicity.simplicityaclientforreddit.main.usecases.compose.NavigationToShowPostsUseCase

@Composable
fun SearchScreen(navigator: NavHostController, logic: SearchLogic, state: UiState<Data>) {
    Log.i("SearchScreen", "State is being updated to -> $state")
    when (state) {
        is UiState.Loading -> ScreenLoading(state.loadingMessage)
        is UiState.Error -> ScreenError()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navigator, state.data.searchResult, logic)
    }
}

@Composable
fun Show(navigator: NavHostController, listOfSubs: List<String>, logic: SearchLogic) {
    var query by remember { mutableStateOf("") }
    DefaultScreen(modifier = Modifier) {
        Column {
            Row(Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Primary,
                        disabledBorderColor = Primary,
                        unfocusedBorderColor = Primary
                    ),
                    value = query,
                    placeholder = { CText(text = "Search") },
                    textStyle = TextStyle(color = OnBackground),
                    onValueChange = {
                        Log.i("SearchScreen", "Input changed to : $it")
                        query = it
                        logic.updatedInput(it)
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = { logic.getNsfwList() }) {
                    CText(text = "NSFW", color = OnPrimary)
                }
            }
            Spacer(Modifier.height(16.dp))
            LazyColumn(content = {
                items(listOfSubs) {
                    CText(
                        Modifier.padding(top = 8.dp).padding(8.dp).clickable { NavigationToShowPostsUseCase(navigator, it).execute() },
                        text = "r/$it",
                        color = OnBackground
                    )
                    Divider(modifier = Modifier.padding(bottom = 8.dp))
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), listOf("Testing"), SearchLogic())
    }
}
