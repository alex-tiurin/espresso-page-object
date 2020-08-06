package com.atiurin.espressopageobjectexample.framework.utils

import org.junit.Assert

object AssetUtils {
    fun assertException(expected: Boolean = true, block: () -> Unit) {
        var exceptionOccurs = false
        try {
            block()
        } catch (ex: Throwable) {
            exceptionOccurs = true
        }
        Assert.assertEquals(expected, exceptionOccurs)
    }
}