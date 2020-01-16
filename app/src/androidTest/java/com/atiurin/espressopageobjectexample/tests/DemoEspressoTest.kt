package com.atiurin.espressopageobjectexample.tests

import com.atiurin.espressopageobjectexample.data.repositories.CONTACTS
import com.atiurin.espressopageobjectexample.pages.ChatPage
import com.atiurin.espressopageobjectexample.pages.FriendsListPage
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
        FriendsListPage().openChat("Chandler Bing")
        ChatPage()
            .clearHistory()
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


    @Test
    fun testListSize(){
        FriendsListPage().assertFriendsListSize(CONTACTS.size)
    }
}