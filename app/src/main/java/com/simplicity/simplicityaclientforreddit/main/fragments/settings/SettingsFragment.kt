package com.simplicity.simplicityaclientforreddit.main.fragments.settings

import android.os.Bundle
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.databinding.FragmentSettingsBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.main.base.SingleFragmentActivity
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class SettingsFragment : BaseTestFragment<FragmentSettingsBinding, SettingsViewModel>
(SettingsViewModel::class.java, FragmentSettingsBinding::inflate) {

    override fun ready(savedInstanceState: Bundle?) {
        viewModel.setUpSetting(binding.nsfwContent.switch1, getString(R.string.settings_nsfw_content), SettingsSP.KEY_SETTINGS_NSFW, true)
        viewModel.setUpSetting(binding.scrollByBottomPart.switch1, getString(R.string.settings_scroll_bottom), SettingsSP.KEY_SETTINGS_SCROLL_BOTTOM, true)
        viewModel.setUpSetting(binding.scrollByVolumeButtons.switch1, getString(R.string.settings_scroll_volume), SettingsSP.KEY_SETTINGS_SCROLL_VOLUME, false)
        viewModel.setUpSetting(binding.useCache.switch1, getString(R.string.settings_use_cache_for_posts), SettingsSP.KEY_SETTINGS_USE_CACHE, true)
        viewModel.setUpSetting(binding.showListOrSingle.switch1, getString(R.string.settings_show_list_or_single), SettingsSP.KEY_SETTINGS_USE_LIST, true)
        viewModel.setUpSetting(binding.showListOrSingle.switch1, getString(R.string.settings_expand_post_link_in_webview), SettingsSP.KEY_SETTINGS_SHOW_LINK_IN_WEB_VIEW_UNDER_POST, true)

        // Hidden subs
        binding.hiddenSubsSetting.settingTitle.text = resources.getText(R.string.hidden_subs)
        binding.hiddenSubsSetting.mainLayout.setOnClickListener { startSingleActivity(SingleFragmentActivity.FRAGMENT_VALUE_SETTINGS_HIDDEN_SUBS) }
    }
}
