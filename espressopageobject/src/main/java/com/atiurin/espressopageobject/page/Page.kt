package com.atiurin.espressopageobject.page

abstract class Page<out T : Page<T>>{
    inline fun <reified T : Page<T>> doOnPage(noinline function: T.() -> Unit): T {
        return T::class.java
            .newInstance()
            .apply { this(function) }
    }

    inline operator fun <R> invoke(block: T.() -> R): R {
        return block.invoke(this as T)
    }
}