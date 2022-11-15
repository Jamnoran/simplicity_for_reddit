package com.simplicity.simplicityaclientforreddit.main.fragments.search

import android.os.Bundle
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.BaseActivity
import com.simplicity.simplicityaclientforreddit.main.fragments.search.ui.main.SearchFragment

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}
