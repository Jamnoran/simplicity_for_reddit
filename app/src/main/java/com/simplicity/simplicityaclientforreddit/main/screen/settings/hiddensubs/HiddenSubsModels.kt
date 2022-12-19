package com.simplicity.simplicityaclientforreddit.main.screen.settings.hiddensubs

import com.simplicity.simplicityaclientforreddit.main.media.TesterHelper
import com.simplicity.simplicityaclientforreddit.main.models.internal.HiddenSubs

data class Data(val hiddenSubs: List<HiddenSubs> = emptyList()) {
    companion object {
        fun preview(): Data {
            return Data(listOf(TesterHelper.getHiddenSub()))
        }
    }
}
