package com.atiurin.espressopageobject.extensions.executors

import android.os.SystemClock
import androidx.test.espresso.ViewInteraction
import com.atiurin.espressopageobject.extensions.ViewActionsConfig
import com.atiurin.espressopageobject.extensions.entities.EspressoAction

class ViewInteractionActionExecutor(val viewInteraction: ViewInteraction, val action: EspressoAction) :
    ActionExecutor {
    @Volatile var exception : Throwable? = null

    override fun execute()  {
        var result: Boolean
        val startTime = SystemClock.elapsedRealtime()
        val endTime = startTime + ViewActionsConfig.ACTION_TIMEOUT
        do {
            result = true
            viewInteraction.withFailureHandler { error, viewMatcher ->
                if (error::class.java in ViewActionsConfig.allowedExceptions){
                    result = false
                    exception = error
                } else throw error
            }.perform(action.viewAction)
            if (!result) Thread.sleep(50)
        } while (SystemClock.elapsedRealtime() < endTime && !result)
        if (!result && exception != null){
            throw exception as Throwable
        }
    }

    override fun getEspressoAction(): EspressoAction {
        return action
    }
}