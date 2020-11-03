package com.atiurin.espressopageobjectexample.framework.utils

import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry

object EspressoUtil {
    fun openOptionsMenu() = apply {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().context)
    }
}