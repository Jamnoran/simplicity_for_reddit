package com.simplicity.simplicityaclientforreddit.main.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import com.simplicity.simplicityaclientforreddit.main.components.screens.DefaultScreen
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenError
import com.simplicity.simplicityaclientforreddit.main.components.screens.ScreenLoading
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute
import com.simplicity.simplicityaclientforreddit.main.theme.SimplicityAClientForRedditTheme

@Composable
fun SettingsScreen(navController: NavHostController, logic: SettingsLogic, state: UiState<Data>) {
    when (state) {
        is UiState.Error -> ScreenError()
        is UiState.Loading -> ScreenLoading()
        is UiState.Empty -> {}
        is UiState.Success -> Show(navController, logic, state.data)
    }
}

@Composable
fun Show(navController: NavHostController, logic: SettingsLogic, data: Data) {
    DefaultScreen(Modifier) {
        Column(Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
            Button(onClick = {
                navController.navigate(NavRoute.HIDDEN_SUBS.path)
            }) {
                Text("Show my hidden subs")
            }
            Spacer(Modifier.height(32.dp))
            // Scroll by using bottom part of app
            SettingsItem(
                SettingsSP.KEY_SETTINGS_SCROLL_BOTTOM,
                stringResource(R.string.settings_scroll_bottom),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_SCROLL_BOTTOM, true)
            )
            // Use caching within app
            SettingsItem(
                SettingsSP.KEY_SETTINGS_USE_CACHE,
                stringResource(R.string.settings_use_cache_for_posts),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_CACHE, true)
            )
            // Navigate by using list or single detail
            SettingsItem(
                SettingsSP.KEY_SETTINGS_USE_LIST,
                stringResource(R.string.settings_show_list_or_single),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, true)
            )
            SettingsItem(
                SettingsSP.KEY_SHOW_LINKS_UNDER_POST,
                stringResource(R.string.settings_show_list_or_single),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_USE_LIST, true)
            )
            SettingsItem(
                SettingsSP.KEY_SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST,
                stringResource(R.string.settings_expand_post_link_in_webview),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST, true)
            )
            SettingsItem(
                SettingsSP.KEY_SETTINGS_NSFW,
                stringResource(R.string.settings_nsfw_content),
                SettingsSP().loadSetting(SettingsSP.KEY_SETTINGS_NSFW, true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SimplicityAClientForRedditTheme {
        Show(rememberNavController(), SettingsLogic(), Data.preview())
    }
}
