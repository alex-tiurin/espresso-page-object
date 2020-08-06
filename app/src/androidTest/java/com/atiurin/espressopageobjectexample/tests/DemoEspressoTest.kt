package com.atiurin.espressopageobjectexample.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUp
import com.atiurin.espressopageobject.testlifecycle.setupteardown.TearDown
import com.atiurin.espressopageobjectexample.activity.MainActivity
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

    private val activityTestRule = ActivityTestRule(MainActivity::class.java)

    init {
        setupRule
            .addSetUp { Log.info("Common setup for all @Tests") }
            .addSetUp (FIRST_CONDITION){ Log.info("$FIRST_CONDITION setup, executed after common setup")  }
            .addSetUp (SECOND_CONDITION){ Log.info("$SECOND_CONDITION setup")  }
            .addSetUp { Log.info("Last common setup for all @Tests") }
            .addTearDown(FIRST_CONDITION) {Log.info("$FIRST_CONDITION teardowm executed before common teardowm")}
            .addTearDown { Log.info("Common tearDown for all @Tests") }
            .addTearDown(SECOND_CONDITION) {Log.info("$SECOND_CONDITION teardowm executed last")}
        ruleSequence.addLast(activityTestRule)
    }

    @Test
    fun friendsItemCheck() {
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice", "Oh. My. God")
    }

    @SetUp(SECOND_CONDITION)
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
    }


    @Test
    fun testListSize(){
        FriendsListPage().assertFriendsListSize(CONTACTS.size)
    }
}