package com.atiurin.espressopageobject.core.espresso

import com.atiurin.espressopageobject.core.common.OperationResult
import com.atiurin.espressopageobject.core.common.OperationResultDescription

class EspressoOperationResultDescription(
    override val result: OperationResult,
    override val description: String
) : OperationResultDescription