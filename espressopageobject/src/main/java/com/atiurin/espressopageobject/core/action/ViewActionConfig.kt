package com.atiurin.espressopageobject.core.action

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException

class ViewActionConfig {
    companion object {
        var ACTION_TIMEOUT = 5_000L
        var LOGCAT_TAG = "EspressoAction"
        var allowedExceptions = mutableListOf<Class<out Throwable>>(
            PerformException::class.java,
            NoMatchingViewException::class.java
        )
    }
}