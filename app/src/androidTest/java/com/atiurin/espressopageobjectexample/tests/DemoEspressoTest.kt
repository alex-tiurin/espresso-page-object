package com.atiurin.espressopageobjectexample.tests

import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.extensions.click
import com.atiurin.espressopageobjectexample.activity.MainActivity
import com.atiurin.espressopageobjectexample.data.repositories.CURRENT_USER
import com.atiurin.espressopageobjectexample.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressopageobjectexample.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressopageobjectexample.managers.AccountManager
import com.atiurin.espressopageobjectexample.pages.ChatPage
import com.atiurin.espressopageobjectexample.pages.FriendsListPage
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DemoEspressoTest : BaseTest() {


    @Test
    fun friendsItemCheck() {
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice", "Oh. My. God")
    }

    @Test
    fun sendMessage() {
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage("test message")
    }

    @Test
    fun checkMessagesPositionsInChat() {
        val firstMessage = "first message"
        val secondMessage = "second message"
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage(firstMessage)
            .sendMessage(secondMessage)
            .assertMessageTextAtPosition(0, firstMessage)
            .assertMessageTextAtPosition(1, secondMessage)

    }
}