package com.atiurin.espressopageobject.extensions.executors

import com.atiurin.espressopageobject.extensions.EspressoAction

interface ActionExecutor{
    fun execute() : ActionExecutor
    fun getEspressoAction() : EspressoAction
}