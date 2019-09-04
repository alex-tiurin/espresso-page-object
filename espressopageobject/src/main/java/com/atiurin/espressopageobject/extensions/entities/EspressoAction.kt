package com.atiurin.espressopageobject.extensions.entities

import androidx.test.espresso.ViewAction
import com.atiurin.espressopageobject.extensions.ViewActionsConfig

open class EspressoAction(val type: ViewActionsConfig.ActionType, val viewAction: ViewAction)