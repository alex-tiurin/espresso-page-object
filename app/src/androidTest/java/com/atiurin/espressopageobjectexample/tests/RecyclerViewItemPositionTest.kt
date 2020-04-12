package com.atiurin.espressopageobjectexample.tests

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.extensions.*
import com.atiurin.espressopageobject.recyclerview.withRecyclerView
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.activity.MainActivity
import com.atiurin.espressopageobjectexample.data.repositories.CONTACTS
import com.atiurin.espressopageobjectexample.framework.Log
import org.hamcrest.Matchers.not
import org.junit.Test

class RecyclerViewItemPositionTest : BaseTest() {
    init {
        ruleSequence.addLast(ActivityTestRule(MainActivity::class.java))
    }

    @Test
    fun testDisplayedItemPositions() {
        for (index in 0..3) {
            withRecyclerView(withId(R.id.recycler_friends)).atPosition(index)
                .assertMatches(hasDescendant(withText(CONTACTS[index].name)))
                .assertMatches(hasDescendant(withText(CONTACTS[index].status)))
                .isDisplayed()
        }
    }

    @Test
    fun testItemNotDisplayed() {
        withId(R.id.navigation_user_avatar).isNotDisplayed()
    }
}
