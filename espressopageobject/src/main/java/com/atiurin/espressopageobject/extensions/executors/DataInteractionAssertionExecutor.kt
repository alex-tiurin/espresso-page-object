package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.DataInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.atiurin.espressopageobject.extensions.EspressoAssertion

class DataInteractionAssertionExecutor(val dataInteraction: DataInteraction, val assertion: EspressoAssertion) : AssertionExecutor {
    override fun execute(){
        dataInteraction.check(matches(assertion.matcher))
    }

    override fun getEspressoAssertion(): EspressoAssertion {
        return assertion
    }
}