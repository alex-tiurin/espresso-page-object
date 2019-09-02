package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.ViewInteraction
import com.atiurin.espressopageobject.extensions.EspressoAction

class ViewInteractionActionExecutor(val viewInteraction: ViewInteraction, val action: EspressoAction) :
    ActionExecutor {

    override fun execute(): ActionExecutor = apply {
        viewInteraction.perform(action.viewAction)
    }

    override fun getEspressoAction(): EspressoAction {
        return action
    }
}