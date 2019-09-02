package com.atiurin.espressopageobjectexample.data.repositories

import android.os.Handler
import com.atiurin.espressopageobjectexample.adapters.ContactAdapter
import com.atiurin.espressopageobjectexample.data.entities.Contact

object ContactRepositoty {
//    fun getAll(adapter: ContactAdapter) {
//////        ContactsIdlingResource.getInstanceFromApp()?.setIdleState(false)
////        Handler().postDelayed({
////            adapter.updateData(contacts)
////            adapter.notifyDataSetChanged()
//////            ContactsIdlingResource.getInstanceFromApp()?.setIdleState(true)
////        }, 200)
////    }

    fun getContact(id: Int) : Contact{
        return contacts.find { it.id == id }!!
    }

    private val contacts = CONTACTS
}