package com.atiurin.espressopageobject.core.espresso

import com.atiurin.espressopageobject.core.common.Operation
import com.atiurin.espressopageobject.core.common.OperationResult
import com.atiurin.espressopageobject.core.common.OperationResultDescription

class EspressoOperationResult(
    override val operation: Operation,
    override val success: Boolean,
    override val exception: Throwable? = null,
    override var resultDescription: OperationResultDescription? = null
) : OperationResult