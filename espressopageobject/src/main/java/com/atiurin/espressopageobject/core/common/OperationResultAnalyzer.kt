package com.atiurin.espressopageobject.core.common

interface OperationResultAnalyzer {
    /**
     * @return success status of operation execution
     */
    fun analyze(operationResult: OperationResult) : Boolean
}