package com.atiurin.espressopageobject.core.espresso.action

import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import com.atiurin.espressopageobject.core.common.Operation
import com.atiurin.espressopageobject.core.common.OperationIterationResult
import com.atiurin.espressopageobject.core.common.OperationType
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

interface EspressoAction : Operation {
    val viewAction: ViewAction
    override val name: String
    override val type: OperationType
    override val description: String
    override val timeoutMs: Long
}