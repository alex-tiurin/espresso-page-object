package com.atiurin.espressopageobjectexample.framework

import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry

interface Page{
    fun assertPageDisplayed() : Page

    fun openOptionsMenu() = apply {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().context)
    }
}