package com.atiurin.espressopageobjectexample.async

import com.atiurin.espressopageobjectexample.data.entities.Contact
import com.atiurin.espressopageobjectexample.data.repositories.CONTACTS
import kotlinx.coroutines.delay

class GetContacts : UseCase<ArrayList<Contact>, UseCase.None>() {

    override suspend fun run(params: None): Either<Exception, ArrayList<Contact>> {
        return try {
            delay(500)
            val contacts = CONTACTS
            Success(contacts)
        } catch (e: Exception) {
            Failure(e)
        }
    }
}