package com.atiurin.espressopageobject.core.action

import com.atiurin.espressopageobject.core.executors.ActionExecutor

interface ViewActionProcessor {
    fun process(actionExecutor: ActionExecutor)
}