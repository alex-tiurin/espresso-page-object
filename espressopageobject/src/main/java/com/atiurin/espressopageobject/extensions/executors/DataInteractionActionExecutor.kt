package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.DataInteraction
import com.atiurin.espressopageobject.extensions.EspressoAction

class DataInteractionActionExecutor(val dataInteraction: DataInteraction, val action: EspressoAction):
    ActionExecutor {
    override fun execute(): ActionExecutor = apply {
        dataInteraction.perform(action.viewAction)
    }

    override fun getEspressoAction(): EspressoAction {
        return action
    }

}