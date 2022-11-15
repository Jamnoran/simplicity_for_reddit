package com.simplicity.simplicityaclientforreddit.main.usecases.text

import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class GetTimeAgoUseCase() {
    fun execute(time: Long?): String {
        time?.let {
            val prettyTime = PrettyTime(Locale.getDefault())
            return prettyTime.format(Date((time * 1000)))
        }
        return ""
    }
}
