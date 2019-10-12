package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.atiurin.espressopageobject.extensions.EspressoAssertion
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig

open class ViewInteractionAssertionExecutor(val viewInteraction: ViewInteraction, val assertion: EspressoAssertion):
    AssertionExecutor {
    override fun execute(){
        var result: Boolean
        var exception: Throwable? = null
        val startTime = System.currentTimeMillis()
        val endTime = startTime + ViewActionsConfig.ACTION_TIMEOUT
        do {
            result = true
            viewInteraction.withFailureHandler { error, viewMatcher ->
                if (error::class.java in ViewAssertionsConfig.allowedExceptions){
                    result = false
                    exception = error
                } else throw error
            }.check(matches(assertion.matcher))
            if (!result) Thread.sleep(50)
        } while (System.currentTimeMillis() < endTime && !result)
        if (!result && exception != null){
            throw exception as Throwable
        }
    }

    override fun getEspressoAssertion(): EspressoAssertion {
        return assertion
    }

}