package com.atiurin.espressopageobjectexample.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.*
import com.atiurin.espressopageobject.extensions.hasText
import com.atiurin.espressopageobject.extensions.isDisplayed
import com.atiurin.espressopageobject.recyclerview.RecyclerViewItem
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.framework.Page
import com.atiurin.espressopageobjectexample.framework.step
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

class FriendsListPage : Page {
    private val friendsList = withId(R.id.recycler_friends)//withTagValue(`is`(Tags.CONTACTS_LIST))

    override fun assertPageDisplayed() = apply {
        step("Assert friends list page displayed") {
            friendsList.isDisplayed()
        }
    }

    class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>, autoScroll: Boolean = true) :
        RecyclerViewItem(list, item, autoScroll) {
        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
    }

    fun getListItem(title: String): FriendRecyclerItem {
        return FriendRecyclerItem(
            withId(R.id.recycler_friends),
            hasDescendant(allOf(withId(R.id.tv_name), withText(title)))
        )
    }

    fun openChat(name: String) = apply {
        step("Open chat with friend '$name'") {
            this.getListItem(name).click()
            ChatPage().assertPageDisplayed()
        }
    }

    fun assertStatus(name: String, status: String) = apply {
        step("Assert friend with name '$name' has status '$status'") {
            this.getListItem(name).status.hasText(status)
        }
    }

    fun assertName(nameText: String) = apply {
        step("Assert friend name '$nameText' in the right place") {
            this.getListItem(nameText).name.hasText(nameText)
        }
    }
}
