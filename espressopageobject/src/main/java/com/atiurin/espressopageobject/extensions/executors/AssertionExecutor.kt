package com.atiurin.espressopageobject.extensions.executors

import com.atiurin.espressopageobject.extensions.EspressoAssertion

interface AssertionExecutor{
    fun execute() : AssertionExecutor
    fun getEspressoAssertion() : EspressoAssertion
}