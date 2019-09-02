package com.atiurin.espressopageobjectexample.idlingresources.resources

import com.atiurin.espressopageobjectexample.idlingresources.AbstractIdlingResource
import com.atiurin.espressopageobjectexample.idlingresources.Holder

class ContactsIdlingResource : AbstractIdlingResource(){
    companion object : Holder<ContactsIdlingResource>(::ContactsIdlingResource)
}
