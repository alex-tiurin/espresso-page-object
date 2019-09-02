package com.atiurin.espressopageobjectexample.idlingresources

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.atiurin.espressopageobjectexample.MyApplication

open class Holder<out T>(private val constructor: () -> T) {

    @Volatile
    private var instance: T? = null

    @VisibleForTesting
    fun getInstanceFromTest(): T? {
        return when {
            instance != null -> instance
            else -> synchronized(this) {
                instance = constructor()
                instance
            }
        }
    }

    fun getInstanceFromApp(): T? {
        return instance
    }
}