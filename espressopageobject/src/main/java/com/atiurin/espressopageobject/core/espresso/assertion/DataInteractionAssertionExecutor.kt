package com.atiurin.espressopageobject.core.espresso.assertion

import com.atiurin.espressopageobject.core.espresso.action.EspressoOperationExecutor

open class DataInteractionAssertionExecutor(
    val assertion: EspressoAssertion
) : EspressoOperationExecutor(assertion)