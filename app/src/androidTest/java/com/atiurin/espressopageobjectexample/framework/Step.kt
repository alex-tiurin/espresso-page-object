package com.atiurin.espressopageobjectexample.framework

import android.util.Log

fun     step(description: String, action: () -> Unit){
    Log.d("Espresso step", description)
    action()
}