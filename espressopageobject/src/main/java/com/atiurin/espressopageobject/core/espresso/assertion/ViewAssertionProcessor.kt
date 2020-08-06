package com.atiurin.espressopageobject.core.espresso.assertion

import com.atiurin.espressopageobject.core.espresso.EspressoExecutor
import com.atiurin.espressopageobject.core.espresso.EspressoOperationResult

interface ViewAssertionProcessor {
    fun process(executor: EspressoExecutor): EspressoOperationResult
}