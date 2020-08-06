package com.atiurin.espressopageobject.core.espresso.action

import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewAction
import com.atiurin.espressopageobject.core.common.OperationIterationResult
import com.atiurin.espressopageobject.core.common.OperationType

class DataInteractionEspressoAction(
    val dataInteraction: DataInteraction,
    override val viewAction: ViewAction,
    override val name: String,
    override val type: OperationType,
    override val description: String,
    override val timeoutMs: Long
) : EspressoAction {
    override fun execute() : OperationIterationResult {
        var success = true
        var exception: Throwable? = null
        try {
            dataInteraction.perform(viewAction)
        }catch (error: Throwable){
            success = false
            exception = error
        }
        return OperationIterationResult(success, exception)
    }
}