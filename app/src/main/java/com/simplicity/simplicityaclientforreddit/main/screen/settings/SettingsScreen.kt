package com.simplicity.simplicityaclientforreddit.main.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.Loading
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute.HIDDEN_SUBS
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun SettingsScreen(navController: NavHostController, logic: SettingsLogic) {
    logic.stateFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> Loading()
            is UiState.Error -> Error()
            is UiState.Success -> Show(navController, state.data) { logic.test(it) }
        }
    }
}

@Composable
fun Show(navController: NavHostController, data: String, test: (String) -> Unit) {
    var dataRemembered by remember { mutableStateOf(data) }
    DefaultScreen(Modifier) {
        Column(Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
            Button(onClick = {
                navController.navigate(HIDDEN_SUBS.path)
            }) {
                Text("Show my hidden subs")
            }
            Spacer(Modifier.height(32.dp))
            SettingsItem(SettingsSP.KEY_SETTINGS_SCROLL_BOTTOM, stringResource(R.string.settings_scroll_bottom), SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_SCROLL_BOTTOM, true))
            SettingsItem(SettingsSP.KEY_SETTINGS_USE_CACHE, stringResource(R.string.settings_use_cache_for_posts), SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_CACHE, true))
            SettingsItem(SettingsSP.KEY_SETTINGS_USE_LIST, stringResource(R.string.settings_show_list_or_single), SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, true))
            CText(text = "Testing \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTest end")
            Button(onClick = {
                test.invoke("Button pressed!")
                navController.navigate(HIDDEN_SUBS.path)
            }) {
                Text(dataRemembered)
            }
            CText(text = "Testing \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nTest end")
        }
    }
}

@Composable
fun SettingsItem(settingsKey: String, text: String, defaultValue: Boolean) {
    var value by remember { mutableStateOf(defaultValue) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        CText(Modifier.fillMaxWidth(.8f), text = text, color = OnBackground)
        Spacer(Modifier.weight(1f))
        Checkbox(checked = value, onCheckedChange = {
            value = it
            SettingsSP().saveSetting(settingsKey, it)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), "Test", {})
    }
}
