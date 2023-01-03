package com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // ktlint-disable no-wildcard-imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.theme.Primary
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun HiddenSubsScreen(navController: NavHostController, logic: HiddenSubsLogic = HiddenSubsLogic()) {
    logic.stateFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> ScreenLoading(state.loadingMessage)
            is UiState.Error -> ScreenError()
            is UiState.Empty -> {}
            is UiState.Success -> Show(navController, state.data, logic)
        }
    }
}

@Composable
fun Show(navController: NavHostController?, data: Data, logic: HiddenSubsLogic) {
    Toast.makeText(
        LocalContext.current,
        "Got this many subs : ${data.hiddenSubs.size}",
        Toast.LENGTH_LONG
    ).show()
    DefaultScreen {
        Column {
            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Button(onClick = {
                    logic.saveSubList()
                }) {
                    Text("Save list online")
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = {
                    logic.loadSubList()
                }) {
                    Text("Import list")
                }
            }
            LazyColumn(
                modifier = Modifier.padding(8.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data.hiddenSubs) { hiddenSub ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                logic.removeSub(hiddenSub.sub)
                            }
                    ) {
                        CText(text = hiddenSub.sub)
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "Remove sub",
                            tint = Primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(null, Data(listOf(TesterHelper.getHiddenSub())), logic = HiddenSubsLogic())
    }
}
