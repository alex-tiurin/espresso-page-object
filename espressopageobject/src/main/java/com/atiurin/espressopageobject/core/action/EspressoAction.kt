package com.atiurin.espressopageobject.core.action

import androidx.test.espresso.ViewAction
import com.atiurin.espressopageobject.core.Description
import com.atiurin.espressopageobject.core.OperationType

open class EspressoAction(val type: OperationType, val viewAction: ViewAction) {
    val description: Description =
        Description(
            type,
            "Action with type '$type', espresso view action '$viewAction'"
        )
}