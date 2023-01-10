package com.simplicity.simplicityaclientforreddit.main.screen.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic.BaseInput
import com.simplicity.simplicityaclientforreddit.main.components.texts.CText
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground

class SettingsInput(val inputData: String = "") : BaseInput

data class Data(val data: String) {
    companion object {
        fun preview(): Data {
            return Data("Preview")
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
