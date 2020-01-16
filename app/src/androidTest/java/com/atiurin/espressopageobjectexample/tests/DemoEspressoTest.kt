package com.atiurin.espressopageobjectexample.tests

import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUp
import com.atiurin.espressopageobject.testlifecycle.setupteardown.TearDown
import com.atiurin.espressopageobjectexample.data.repositories.CONTACTS
import com.atiurin.espressopageobjectexample.framework.Log
import com.atiurin.espressopageobjectexample.pages.ChatPage
import com.atiurin.espressopageobjectexample.pages.FriendsListPage
import org.junit.Test

class DemoEspressoTest : BaseTest() {
    companion object{
        const val FIRST_CONDITION = "FIRST_CONDITION"
        const val SECOND_CONDITION = "SECOND_CONDITION"
    }
    init {
        setupRule
            .addSetUp { Log.info("Common setup for all @Tests") }
            .addSetUp (FIRST_CONDITION){ Log.info("$FIRST_CONDITION setup, executed after common setup")  }
            .addSetUp (SECOND_CONDITION){ Log.info("$SECOND_CONDITION setup")  }
            .addTearDown(FIRST_CONDITION) {Log.info("$FIRST_CONDITION teardowm executed before common teardowm")}
            .addTearDown { Log.info("Common setup for all @Tests") }
            .addTearDown(SECOND_CONDITION) {Log.info("$SECOND_CONDITION teardowm executed last")}
    }

    @Test
    fun friendsItemCheck() {
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice", "Oh. My. God")
    }

    @SetUp(FREQUENT_SETUP_KEY, SECOND_CONDITION)
    @Test
    fun sendMessage() {
        Log.info("Start test sendMessage")
        FriendsListPage().openChat("Chandler Bing")
        ChatPage()
            .clearHistory()
            .sendMessage("test message")
    }

    @SetUp(FIRST_CONDITION, SECOND_CONDITION)
    @TearDown(FIRST_CONDITION, SECOND_CONDITION)
    @Test
    fun checkMessagesPositionsInChat() {
        Log.info("Start test checkMessagesPositionsInChat")
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