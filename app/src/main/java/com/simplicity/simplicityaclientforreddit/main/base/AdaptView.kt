package com.simplicity.simplicityaclientforreddit.main.base

import android.content.Context
import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simplicity.simplicityaclientforreddit.R

class AdaptView {
    private var _recyclerview: RecyclerView? = null
    fun setUpAdapter(recyclerView: RecyclerView, resources: Resources, context: Context) {
        _recyclerview = recyclerView
        _recyclerview?.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(context)

            val adapter = BaseAdapter()

            recyclerView.adapter = adapter

            val dividerItemDecoration = DividerItemDecoration(recyclerView.context, RecyclerView.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
                ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    fun submitList(list: ArrayList<Any>) {
        (_recyclerview?.adapter as BaseAdapter).submitListAny(list)
    }
}
