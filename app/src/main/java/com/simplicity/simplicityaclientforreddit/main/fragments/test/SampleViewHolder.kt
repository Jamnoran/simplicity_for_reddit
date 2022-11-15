package com.simplicity.simplicityaclientforreddit.main.fragments.test

import android.view.View
import android.widget.TextView
import com.simplicity.simplicityaclientforreddit.R
import com.simplicity.simplicityaclientforreddit.main.base.BaseViewHolderItem

class SampleViewHolder(var testData: SampleData) : BaseViewHolderItem(testData) {

    override fun getLayout() = R.layout.base_adapter_item

    override fun bind(itemView: View) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        textView.text = testData.text
    }
}
