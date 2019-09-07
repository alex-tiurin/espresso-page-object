package com.atiurin.espressopageobject.extensions.executors

import androidx.test.espresso.ViewInteraction
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.entities.EspressoAction

class ViewInteractionActionExecutor(val viewInteraction: ViewInteraction, val action: EspressoAction) :
    ActionExecutor {
    @Volatile var exception : Throwable? = null

    override fun execute()  {
        val result = booleanArrayOf(true)
        val startTime = System.currentTimeMillis()
        val endTime = startTime + ViewActionsConfig.ACTION_TIMEOUT
        do {
            result[0] = true
            viewInteraction.withFailureHandler { error, viewMatcher ->
                if (error::class.java in ViewActionsConfig.allowedExceptions){
                    result[0] = false
                    exception = error
                } else throw error
            }.perform(action.viewAction)
            Thread.sleep(50)
        } while (System.currentTimeMillis() < endTime && !result[0])
        if (!result[0] && exception != null){
            throw exception as Throwable
        }
    }

    override fun getEspressoAction(): EspressoAction {
        return action
    }
}