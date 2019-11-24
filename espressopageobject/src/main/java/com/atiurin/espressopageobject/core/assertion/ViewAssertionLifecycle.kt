package com.atiurin.espressopageobject.core.assertion

import com.atiurin.espressopageobject.core.AbstractOperationLifecycle
import com.atiurin.espressopageobject.core.executors.AssertionExecutor


object ViewAssertionLifecycle : AbstractOperationLifecycle() {
    var currentEspressoAssertion: EspressoAssertion? = null

    fun assertView(assertionExecutor: AssertionExecutor) {
        currentEspressoAssertion = assertionExecutor.getEspressoAssertion()
        val description = assertionExecutor.getEspressoAssertion().description
        getListeners().forEach { it.before(description) }
        try {
            assertionProcessor.assert(assertionExecutor)
            getListeners().forEach { it.afterSuccess(description) }
        } catch (th: Throwable) {
            getListeners().forEach { it.afterFailure(description, th) }
            throw th
        } finally {
            getListeners().forEach { it.after(description) }
        }
    }

    //set your own implementation of ViewAssertionProcessor in case you would like to customise the behaviour
    var assertionProcessor = object : ViewAssertionProcessor {
        override fun assert(assertionExecutor: AssertionExecutor) {
            assertionExecutor.execute()
        }
    }

}