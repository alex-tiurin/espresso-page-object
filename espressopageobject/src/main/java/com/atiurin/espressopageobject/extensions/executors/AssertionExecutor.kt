package com.atiurin.espressopageobject.extensions.executors

import com.atiurin.espressopageobject.extensions.EspressoAssertion

interface AssertionExecutor{
    fun execute()
    fun getEspressoAssertion() : EspressoAssertion
}