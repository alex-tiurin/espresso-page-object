package com.atiurin.espressopageobject.core.espresso.action

import com.atiurin.espressopageobject.core.espresso.EspressoExecutor
import com.atiurin.espressopageobject.core.espresso.EspressoOperationResult

interface ViewActionProcessor {
    fun process(executor: EspressoExecutor) : EspressoOperationResult
}