package com.simplicity.simplicityaclientforreddit.main.usecases.cachedPosts

import com.google.gson.Gson
import com.simplicity.simplicityaclientforreddit.main.io.settings.SettingsSP

class RemoveAllCachedPostUseCase() {
    fun execute() {
        val array = GetCachedPostUseCase().execute()
        val mutableList = array.toMutableList()
        mutableList.clear()
        val newValueToStore = Gson().toJson(mutableList)
        SettingsSP().saveSetting(SettingsSP.KEY_CACHED_POSTS, newValueToStore)
    }
}
