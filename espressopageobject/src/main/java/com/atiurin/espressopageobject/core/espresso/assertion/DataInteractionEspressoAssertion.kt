package com.atiurin.espressopageobject.core.espresso.assertion

import android.view.View
import androidx.test.espresso.DataInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import com.atiurin.espressopageobject.core.common.OperationIterationResult
import com.atiurin.espressopageobject.core.common.OperationType
import org.hamcrest.Matcher

class DataInteractionEspressoAssertion (
    val dataInteraction: DataInteraction,
    override val matcher: Matcher<View>,
    override val name: String,
    override val type: OperationType,
    override val description: String,
    override val timeoutMs: Long
): EspressoAssertion{
    override fun execute() : OperationIterationResult {
        var success = true
        var exception: Throwable? = null
        try {
            dataInteraction.check(matches(matcher))
        }catch (error: Throwable){
            success = false
            exception = error
        }
        return OperationIterationResult(success, exception)
    }
}