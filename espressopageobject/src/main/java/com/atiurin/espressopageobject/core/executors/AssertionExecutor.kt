package com.atiurin.espressopageobject.core.executors

import com.atiurin.espressopageobject.core.assertion.EspressoAssertion


interface AssertionExecutor{
    fun execute()
    fun getEspressoAssertion() : EspressoAssertion
}