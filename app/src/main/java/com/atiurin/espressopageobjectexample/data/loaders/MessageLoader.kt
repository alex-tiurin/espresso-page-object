package com.atiurin.espressopageobjectexample.data.loaders

import com.atiurin.espressopageobjectexample.data.entities.Message
import com.atiurin.espressopageobjectexample.data.repositories.MESSAGES

open class MessageLoader{
    open fun load() : ArrayList<Message>{
        return MESSAGES
    }
}