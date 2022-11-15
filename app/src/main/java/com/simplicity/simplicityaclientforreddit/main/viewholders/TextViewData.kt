package com.simplicity.simplicityaclientforreddit.main.viewholders

import com.simplicity.simplicityaclientforreddit.main.base.BaseData

data class TextViewData(val value: String, val onClick: () -> Unit) : BaseData()
