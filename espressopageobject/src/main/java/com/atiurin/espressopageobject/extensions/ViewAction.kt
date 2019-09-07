package com.atiurin.espressopageobject.extensions

import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.PerformException
import com.atiurin.espressopageobject.extensions.entities.EspressoAction
import com.atiurin.espressopageobject.extensions.executors.ActionExecutor

fun actionOnView(actionExecutor: ActionExecutor) {
    ViewActionsConfig.currentEspressoAction = actionExecutor.getEspressoAction()
    ViewActionsConfig.beforeAction()
    ViewActionsConfig.actionProcessor.process(actionExecutor)
    ViewActionsConfig.afterAction()
}

object ViewActionsConfig {
    var beforeAction = {}
    var afterAction = {}
    var currentEspressoAction: EspressoAction? = null

    //set your own implementation of ViewActionProcessor you would like to customise the behaviour
    var actionProcessor: ViewActionProcessor = object : ViewActionProcessor {
        override fun process(actionExecutor: ActionExecutor) {
            actionExecutor.execute()
        }
    }

    enum class ActionType {
        CLICK, LONG_CLICK, DOUBLE_CLICK,
        TYPE_TEXT, REPLACE_TEXT, CLEAR_TEXT, PRESS_KEY, CLOSE_SOFT_KEYBOARD,
        SWIPE_LEFT, SWIPE_RIGHT, SWIPE_UP, SWIPE_DOWN, SCROLL, CUSTOM
    }

    var ACTION_TIMEOUT = 5000L

    var allowedExceptions = mutableListOf<Class<out Throwable>>(PerformException::class.java, NoMatchingViewException::class.java)
}

interface ViewActionProcessor {
    fun process(actionExecutor: ActionExecutor)
}
