package com.atiurin.espressopageobject.extensions


import android.view.View
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.afterAssertion
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.beforeAssertion
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.currentEspressoAssertion
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.viewAssertionProcessor
import com.atiurin.espressopageobject.extensions.executors.AssertionExecutor
import org.hamcrest.Matcher

fun assertView(assertionExecutor: AssertionExecutor) {
    currentEspressoAssertion = assertionExecutor.getEspressoAssertion()
    beforeAssertion()
    viewAssertionProcessor.assert(assertionExecutor)
    afterAssertion()
}

object ViewAssertionsConfig{
    var beforeAssertion= {}
    var afterAssertion= {}
    var currentEspressoAssertion: EspressoAssertion? = null

    //set your own implementation of ViewAssertionProcessor you would like to customise the behaviour
    var viewAssertionProcessor = object : ViewAssertionProcessor{
        override fun assert(assertionExecutor: AssertionExecutor) {
            assertionExecutor.execute()
        }
    }

    enum class AssertionType{
        IS_DISPLAYED, IS_COMPLETELY_DISPLAYED, IS_DISPLAYING_AT_LEAST,
        IS_ENABLED, IS_SELECTED, IS_CLICKABLE, IS_CHECKED, IS_NOT_CHECKED,
        IS_FOCUSABLE, IS_JS_ENABLED, HAS_TEXT, ASSERT_MATCHES
    }
}

open class EspressoAssertion(val type: ViewAssertionsConfig.AssertionType, val matcher: Matcher<View>)

interface ViewAssertionProcessor {
    fun assert(assertionExecutor: AssertionExecutor)
}
