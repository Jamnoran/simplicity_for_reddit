package com.simplicity.simplicityaclientforreddit.main.fragments.settings.hiddenSubs

import android.os.Bundle
import com.simplicity.simplicityaclientforreddit.main.base.BaseTestFragment
import com.simplicity.simplicityaclientforreddit.databinding.FragmentHiddenSubsBinding
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class HiddenSubsFragment : BaseTestFragment<FragmentHiddenSubsBinding, HiddenSubsViewModel>
(HiddenSubsViewModel::class.java, FragmentHiddenSubsBinding::inflate) {

    override fun ready(savedInstanceState: Bundle?) {
        log("Starting fragment")
        viewModel.hiddenSubs().observe(viewLifecycleOwner) { observeHiddenSubs(it) }
        viewModel.getListOfHiddenSubs()
        SettingsSP().loadSetting(SettingsSP.KEY_ACCESS_TOKEN, "")

        binding.updateButton.setOnClickListener {
            viewModel.updateHiddenSubs(binding.hiddenSubs.text.toString())
        }
    }

    private fun observeHiddenSubs(it: String) {
        binding.hiddenSubs.setText(it)
    }
}
