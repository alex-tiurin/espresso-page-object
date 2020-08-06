package com.atiurin.espressopageobject.core.espresso.resultanalyzer

import com.atiurin.espressopageobject.core.common.OperationResult

class DefaultOperationResultAnalyzer : EspressoOperationResultAnalyzer {
    override fun analyze(operationResult: OperationResult): Boolean {
        if (!operationResult.success && operationResult.exception != null) {
            throw operationResult.exception!!
        }
        return operationResult.success
    }
}