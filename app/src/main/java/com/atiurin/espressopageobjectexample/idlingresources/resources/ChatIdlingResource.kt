package com.atiurin.espressopageobjectexample.idlingresources.resources

import com.atiurin.espressopageobjectexample.idlingresources.AbstractIdlingResource
import com.atiurin.espressopageobjectexample.idlingresources.Holder

class ChatIdlingResource : AbstractIdlingResource(){
    companion object : Holder<ChatIdlingResource>(::ChatIdlingResource)
}