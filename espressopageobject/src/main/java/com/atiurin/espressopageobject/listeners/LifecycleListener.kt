package com.atiurin.espressopageobject.listeners

import com.atiurin.espressopageobject.core.Description
import com.atiurin.espressopageobject.core.OperationType
import com.atiurin.espressopageobject.core.action.ActionType
import com.atiurin.espressopageobject.core.assertion.AssertionType

internal interface LifecycleListener{
    /**
     * executed before any action or assertion
     */
    fun before(description: Description)
    /**
     * called when action or assertion has been executed successfully
     */
    fun afterSuccess(description: Description)

    /**
     * called when action or assertion failed
     */
    fun afterFailure(description: Description, throwable: Throwable)

    /**
     * called in any case of action or assertion result
     */
    fun after(description: Description)

    fun doInCase(type: OperationType, caseAction: () -> Unit, caseAssertion: () -> Unit) {
        if (type is ActionType) {
            caseAction()
        } else if (type is AssertionType) {
            caseAssertion()
        }
    }
}