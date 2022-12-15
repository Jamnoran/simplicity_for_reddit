package com.simplicity.simplicityaclientforreddit.main.base.compose

open class BaseComposeLogic<Input> : BaseLogic() {
    var started = false

    fun init(input: Input? = null) {
        if (!checkIfStarted()) {
            if (input != null) {
                ready(input)
            } else {
                ready()
            }
        }
    }

    open fun ready(input: Input) {
    }

    open fun ready() {
    }

    fun checkIfStarted(): Boolean {
        val valueOfFirstStarted = started
        started = true
        return valueOfFirstStarted
    }
}
