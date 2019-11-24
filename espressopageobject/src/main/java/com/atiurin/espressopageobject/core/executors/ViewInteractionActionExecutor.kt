package com.atiurin.espressopageobject.core.executors

import android.os.SystemClock
import androidx.test.espresso.ViewInteraction
import com.atiurin.espressopageobject.core.action.EspressoAction
import com.atiurin.espressopageobject.core.action.ViewActionConfig

class ViewInteractionActionExecutor(val viewInteraction: ViewInteraction, val action: EspressoAction) :
    ActionExecutor {
    var exception : Throwable? = null

    override fun execute()  {
        var result: Boolean
        val endTime = SystemClock.elapsedRealtime() + ViewActionConfig.ACTION_TIMEOUT
        do {
            result = true
            viewInteraction.withFailureHandler { error, viewMatcher ->
                if (error::class.java in ViewActionConfig.allowedExceptions){
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