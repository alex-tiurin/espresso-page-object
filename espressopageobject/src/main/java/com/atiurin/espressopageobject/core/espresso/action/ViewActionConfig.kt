package com.atiurin.espressopageobject.core.espresso.action

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException
import com.atiurin.espressopageobject.core.espresso.EspressoOperationResult
import com.atiurin.espressopageobject.core.espresso.resultanalyzer.DefaultOperationResultAnalyzer

class ViewActionConfig {
    companion object {
        var ACTION_TIMEOUT = 5_000L
        var allowedExceptions = mutableListOf<Class<out Throwable>>(
            PerformException::class.java,
            NoMatchingViewException::class.java
        )
        var defaultResultAnalyzer = DefaultOperationResultAnalyzer()
        val defaultResultHandler: (EspressoOperationResult) -> Unit = {
            defaultResultAnalyzer.analyze(it)
        }
    }
}