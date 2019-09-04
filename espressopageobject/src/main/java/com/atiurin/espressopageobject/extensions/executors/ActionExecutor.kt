package com.atiurin.espressopageobject.extensions.executors

import com.atiurin.espressopageobject.extensions.entities.EspressoAction

interface ActionExecutor{
    fun execute()
    fun getEspressoAction() : EspressoAction
}