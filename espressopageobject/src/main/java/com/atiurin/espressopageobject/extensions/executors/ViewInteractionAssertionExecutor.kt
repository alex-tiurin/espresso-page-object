package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.atiurin.espressopageobject.extensions.EspressoAssertion

class ViewInteractionAssertionExecutor(val viewInteraction: ViewInteraction, val assertion: EspressoAssertion):
    AssertionExecutor {
    override fun execute(): AssertionExecutor = apply{
        viewInteraction.check(matches(assertion.matcher))
    }

    override fun getEspressoAssertion(): EspressoAssertion {
        return assertion
    }

}