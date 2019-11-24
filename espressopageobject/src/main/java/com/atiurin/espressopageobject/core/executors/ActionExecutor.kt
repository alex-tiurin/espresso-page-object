package com.atiurin.espressopageobject.core.executors

import com.atiurin.espressopageobject.core.action.EspressoAction

interface ActionExecutor{
    fun execute()
    fun getEspressoAction() : EspressoAction
}