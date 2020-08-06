package com.atiurin.espressopageobject.core.espresso.assertion

import com.atiurin.espressopageobject.core.espresso.action.EspressoOperationExecutor

open class ViewInteractionAssertionExecutor(
    val assertion: EspressoAssertion
) : EspressoOperationExecutor(assertion)