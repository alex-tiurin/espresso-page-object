package com.atiurin.espressopageobject.core.action

import com.atiurin.espressopageobject.core.AbstractOperationLifecycle
import com.atiurin.espressopageobject.core.executors.ActionExecutor

object ViewActionLifecycle : AbstractOperationLifecycle() {
    var currentEspressoAction: EspressoAction? = null

    fun actionOnView(actionExecutor: ActionExecutor) {
        currentEspressoAction = actionExecutor.getEspressoAction()
        val description = actionExecutor.getEspressoAction().description
        getListeners().forEach { it.before(description) }
        try {
            actionProcessor.process(actionExecutor)
            getListeners().forEach { it.afterSuccess(description) }
        } catch (th: Throwable) {
            getListeners().forEach { it.afterFailure(description, th) }
            throw th
        } finally {
            getListeners().forEach { it.after(description) }
        }
    }

    //set your own implementation of ViewActionProcessor in case you would like to customise the behaviour
    var actionProcessor: ViewActionProcessor = object :
        ViewActionProcessor {
        override fun process(actionExecutor: ActionExecutor) {
            actionExecutor.execute()
        }
    }
}