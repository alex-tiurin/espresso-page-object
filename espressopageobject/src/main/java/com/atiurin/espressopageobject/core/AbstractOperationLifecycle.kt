package com.atiurin.espressopageobject.core

import com.atiurin.espressopageobject.listeners.AbstractLifecycleListener
import com.atiurin.espressopageobject.listeners.LogLifecycleListener

abstract class AbstractOperationLifecycle {
    private var listeners: MutableList<AbstractLifecycleListener> =
        mutableListOf(LogLifecycleListener())

    fun getListeners(): List<AbstractLifecycleListener>{
        return listeners
    }
    fun addListener(listener: AbstractLifecycleListener) {
        val exist = listeners.find { it.id == listener.id }
        if (exist != null) {
            listeners.remove(exist)
        }
        listeners.add(listener)
    }

    fun clearListeners(){
        listeners.clear()
    }

    fun removeListener(listener: AbstractLifecycleListener){
        val exist = listeners.find { it.id == listener.id }
        if (exist != null) {
            listeners.remove(exist)
        }
    }
}