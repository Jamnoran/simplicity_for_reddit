package com.simplicity.simplicityaclientforreddit.main.fragments.settings.hiddenSubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simplicity.simplicityaclientforreddit.databinding.FragmentHiddenSubsBinding
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewModel
import com.simplicity.simplicityaclientforreddit.main.io.room.RoomDB

class HiddenSubsViewModel : BaseViewModel<FragmentHiddenSubsBinding>() {
    private val hiddenSubs = MutableLiveData<String>()

    fun hiddenSubs(): LiveData<String> {
        return hiddenSubs
    }

    fun getListOfHiddenSubs() {
        coroutine {
            val db = RoomDB()
            val listOfHiddenSubs = db.getAllHiddenSubs()
            val stringArray = listOfHiddenSubs.map {
                it.sub
            }
            val joinedString = stringArray.joinToString(",")
            hiddenSubs.postValue(joinedString)
        }
    }

    fun updateHiddenSubs(hiddenSubs: String) {
        coroutine {
            val db = RoomDB()
            db.deleteAllHiddenSubs()
            val array = hiddenSubs.split(",")
            for (sub in array) {
                db.hideSub(sub)
            }
        }
    }
}
