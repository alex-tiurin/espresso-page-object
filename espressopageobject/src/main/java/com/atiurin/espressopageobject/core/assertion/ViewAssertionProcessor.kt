package com.atiurin.espressopageobject.core.assertion

import com.atiurin.espressopageobject.core.executors.AssertionExecutor

interface ViewAssertionProcessor {
    fun assert(assertionExecutor: AssertionExecutor)
}