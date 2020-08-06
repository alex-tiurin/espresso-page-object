package com.atiurin.espressopageobject.core.espresso.assertion

import android.view.View
import com.atiurin.espressopageobject.core.common.Operation
import com.atiurin.espressopageobject.core.common.OperationIterationResult
import com.atiurin.espressopageobject.core.common.OperationType
import com.atiurin.espressopageobject.core.espresso.action.ViewActionConfig
import org.hamcrest.Matcher
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

interface EspressoAssertion : Operation {
    val matcher: Matcher<View>
    override val name: String
    override val type: OperationType
    override val description: String
    override val timeoutMs: Long
}