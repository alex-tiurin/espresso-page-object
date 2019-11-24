package com.atiurin.espressopageobject.core.assertion

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException

class ViewAssertionConfig() {
    companion object {
        var ASSERTION_TIMEOUT = 5_000L
        var LOGCAT_TAG = "EspressoAssertion"
        var allowedExceptions = mutableListOf<Class<out Throwable>>(
            PerformException::class.java,
            NoMatchingViewException::class.java
        )
    }
}