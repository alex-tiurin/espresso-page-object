package com.atiurin.espressopageobject.core.executors

import android.os.SystemClock
import androidx.test.espresso.DataInteraction
import com.atiurin.espressopageobject.core.action.EspressoAction
import com.atiurin.espressopageobject.core.action.ViewActionConfig

class DataInteractionActionExecutor(val dataInteraction: DataInteraction, val action: EspressoAction):
    ActionExecutor {
    var exception : Throwable? = null
    override fun execute() {
        var result: Boolean
        val endTime = SystemClock.elapsedRealtime() + ViewActionConfig.ACTION_TIMEOUT
        do {
            result = true
            try {
                dataInteraction.perform(action.viewAction)
            }catch (th: Throwable){
                if (th::class.java in ViewActionConfig.allowedExceptions){
                    result = false
                    exception = th
                } else throw th
            }
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