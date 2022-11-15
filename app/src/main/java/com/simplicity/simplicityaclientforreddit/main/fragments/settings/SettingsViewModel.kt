package com.simplicity.simplicityaclientforreddit.main.fragments.settings

import androidx.appcompat.widget.SwitchCompat
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewModel
import com.simplicity.simplicityaclientforreddit.databinding.FragmentSettingsBinding
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class SettingsViewModel : BaseViewModel<FragmentSettingsBinding>() {

    fun setUpSetting(switch: SwitchCompat, description: String, settingKey: String, defaultValue: Boolean) {
        switch.text = description
        switch.isChecked = SettingsSP().loadSetting(settingKey, defaultValue)
        switch.setOnCheckedChangeListener { _, checked ->
            SettingsSP().saveSetting(
                settingKey,
                checked
            )
        }
    }
}
