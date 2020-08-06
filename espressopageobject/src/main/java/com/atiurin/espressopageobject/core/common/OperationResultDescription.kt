package com.atiurin.espressopageobject.core.common

import com.atiurin.espressopageobject.core.common.OperationResult

/**
 * Interface contains references to [OperationResult]
 * it also contains text description of the result
 */
interface OperationResultDescription {
    val result: OperationResult
    val description: String
}
