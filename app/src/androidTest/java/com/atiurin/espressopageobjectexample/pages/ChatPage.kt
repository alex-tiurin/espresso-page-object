package com.atiurin.espressopageobjectexample.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.framework.*
import com.atiurin.espressopageobject.extensions.click
import com.atiurin.espressopageobject.extensions.hasText
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.extensions.typeText
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class ChatPage : Page {
    override fun assertPageDisplayed() = apply {
        step("Assert friends list page displayed") {
            messagesList.isDisplayed()
        }
    }

    val messagesList = withId(R.id.messages_list)
    val clearHistoryBtn = withText("Clear history")
    val inputMessageText = withId(R.id.message_input_text)
    val sendMessageBtn = withId(R.id.send_button)

    fun getListItem(text: String): ChatRecyclerItem {
        return ChatRecyclerItem(
            messagesList,
            ViewMatchers.hasDescendant(
                allOf(
                    withId(R.id.message_text),
                    withText(text)
                )
            )
        )
    }

    private fun getListItemAtPosition(position: Int): ChatRecyclerItem {
        return ChatRecyclerItem(messagesList, position)
    }

    fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }

    class ChatRecyclerItem : RecyclerViewItem {
        constructor(list: Matcher<View>, item: Matcher<View>) : super(list, item)
        constructor(list: Matcher<View>, position: Int) : super(list, position)

        val text = getChildMatcher(withId(R.id.message_text))
    }

    fun sendMessage(text: String) = apply {
        step("Send message with text '$text") {
            inputMessageText.typeText(text)
            sendMessageBtn.click()
            this.getListItem(text).text
                .isDisplayed()
                .hasText(text)
        }
    }

    fun assertMessageTextAtPosition(position: Int, text: String) = apply {
        this.getListItemAtPosition(position).text.isDisplayed().hasText(text)
    }

    fun clearHistory() = apply {
        step("Clear chat history") {
            openOptionsMenu()
            clearHistoryBtn.click()
        }
    }

    fun assertMessageDisplayed(text: String) {
        getListItem(text).text
            .isDisplayed()
            .hasText(text)
    }
}