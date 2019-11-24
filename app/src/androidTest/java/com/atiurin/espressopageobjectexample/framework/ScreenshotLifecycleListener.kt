package com.atiurin.espressopageobjectexample.framework

import com.atiurin.espressopageobject.core.Description
import com.atiurin.espressopageobject.listeners.AbstractLifecycleListener

class ScreenshotLifecycleListener : AbstractLifecycleListener(){
    override fun before(description: Description) {
    }

    override fun afterSuccess(description: Description) {
    }

    override fun afterFailure(description: Description, throwable: Throwable) {
    }

    override fun after(description: Description) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}