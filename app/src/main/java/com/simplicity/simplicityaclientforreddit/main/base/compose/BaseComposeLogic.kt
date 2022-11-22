package com.simplicity.simplicityaclientforreddit.main.base.compose

open class BaseComposeLogic<Input> : BaseLogic() {
    var started = false

    fun init(input: Input) {
        if (!checkIfStarted()) {
            ready(input)
        }
    }

    open fun ready(input: Input) {
    }

    fun checkIfStarted(): Boolean {
        val valueOfFirstStarted = started
        started = true
        return valueOfFirstStarted
    }
}
