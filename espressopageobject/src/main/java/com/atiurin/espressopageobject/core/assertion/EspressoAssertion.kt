package com.atiurin.espressopageobject.core.assertion

import android.view.View
import com.atiurin.espressopageobject.core.Description
import com.atiurin.espressopageobject.core.OperationType
import org.hamcrest.Matcher

open class EspressoAssertion(val type: OperationType, val matcher: Matcher<View>){
    val description: Description =
        Description(
            type,
            "Assertion with type '$type' using matcher '$matcher'"
        )
}