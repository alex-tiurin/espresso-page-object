package com.atiurin.espressopageobjectexample.tests

import android.content.Intent
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.extensions.click
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.extensions.isNotDisplayed
import com.atiurin.espressopageobject.extensions.isSuccess
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import com.atiurin.espressopageobjectexample.activity.ChatActivity
import com.atiurin.espressopageobjectexample.activity.INTENT_CONTACT_ID_EXTRA_NAME
import com.atiurin.espressopageobjectexample.framework.utils.AssetUtils.assertException
import com.atiurin.espressopageobjectexample.pages.ChatPage
import org.junit.Test

class ViewInteractionAssertionTest : BaseTest() {

    private val activityTestRule = ActivityTestRule(ChatActivity::class.java, false, false)

    val setUpTearDownRule = SetUpTearDownRule().addSetUp {
        val intent = Intent()
        intent.putExtra(INTENT_CONTACT_ID_EXTRA_NAME, 2)
        activityTestRule.launchActivity(intent)
    }

    init {
        ruleSequence.add(setUpTearDownRule, activityTestRule)
    }

    val chatPage = ChatPage()

    @Test
    fun isSuccessTest() {
        if (!chatPage.clearHistoryBtn.isSuccess { isDisplayed(1000) }) {
            chatPage.openOptionsMenu()
        }
        chatPage.clearHistoryBtn.click()
    }

    @Test
    fun isDisplayed_ofDisplayedObject_Test() {
        chatPage.sendMessageBtn.isDisplayed()
    }

    @Test
    fun isDisplayed_ofNotDisplayedObject_Test() {
        assertException { chatPage.clearHistoryBtn.isDisplayed(1000) }
    }

    @Test
    fun isNotDisplayed_ofDisplayedObject(){
        assertException { chatPage.clearHistoryBtn.isNotDisplayed() }
    }

    @Test
    fun isNotDisplayed_ofNotDisplayedObject(){
        chatPage.clearHistoryBtn.isNotDisplayed()
    }
}