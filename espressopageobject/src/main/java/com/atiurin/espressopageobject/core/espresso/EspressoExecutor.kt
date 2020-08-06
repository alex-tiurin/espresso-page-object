package com.atiurin.espressopageobject.core.espresso

import com.atiurin.espressopageobject.core.common.Operation

interface EspressoExecutor {
    fun execute() : EspressoOperationResult
    fun getOperation() : Operation
}