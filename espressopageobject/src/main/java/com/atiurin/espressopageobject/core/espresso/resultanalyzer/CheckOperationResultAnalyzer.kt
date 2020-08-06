package com.atiurin.espressopageobject.core.espresso.resultanalyzer

import com.atiurin.espressopageobject.core.common.OperationResult

class CheckOperationResultAnalyzer : EspressoOperationResultAnalyzer {
    override fun analyze(operationResult: OperationResult): Boolean {
        return operationResult.success
    }
}