package com.atiurin.espressopageobjectexample.pages

import android.view.View
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.data.Tags
import com.atiurin.espressopageobjectexample.framework.*
import com.atiurin.espressopageobject.extensions.hasText
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf

class FriendsListPage : Page {
    val list = withTagValue(`is`(Tags.CONTACTS_LIST))

    override fun assertPageDisplayed() = apply {
        step("Assert friends list page displayed"){
            list.isDisplayed()
        }
    }

    fun getListItem(title: String): FriendRecyclerItem {
        return FriendRecyclerItem(
            list,
            hasDescendant(
                allOf(
                    ViewMatchers.withId(R.id.tv_name),
                    ViewMatchers.withText(title)
                )
            )
        )
    }

    class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>) : RecyclerViewItem(list, item) {
        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
    }

    fun openChat(name: String) = apply {
        step("Open chat with friend '$name'") {
            getListItem(name).click()
            ChatPage().assertPageDisplayed()
        }
    }

    fun assertStatus(name: String, status: String) = apply {
        step("Assert friend with name '$name' has status '$status'") {
            getListItem(name).status.hasText(status)
        }
    }

    fun assertName(nameText: String) = apply {
        step("Assert friend name '$nameText' in the right place") {
            getListItem(nameText).name.hasText(nameText)
        }
    }
}