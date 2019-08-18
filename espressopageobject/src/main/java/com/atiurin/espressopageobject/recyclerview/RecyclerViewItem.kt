package com.atiurin.espressopageobject.recyclerview

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.v7.widget.RecyclerView
import android.view.View
import com.atiurin.espressopageobject.extensions.*
import org.hamcrest.Matcher

open class RecyclerViewItem(
    private val recyclerViewMatcher: Matcher<View>,
    private val itemViewMatcher: Matcher<View>,
    autoScroll: Boolean = true){

    init {
        if (autoScroll){
            scrollToItem()
        }
    }

    //TODO work around this trade off
    fun scrollToItem() : RecyclerViewItem = apply {
        onView(recyclerViewMatcher)
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher))
    }

    private fun get() : Matcher<View> {
        return withRecyclerView(recyclerViewMatcher).atItem(itemViewMatcher)
    }

    fun getChildMatcher(childMatcher: Matcher<View>)
            = withRecyclerView(recyclerViewMatcher).atItemChild(itemViewMatcher, childMatcher)

    //actions
    fun click() = apply { this.get().click() }
    fun longClick() = apply { this.get().longClick() }
    fun doubleClick() = apply { this.get().doubleClick() }
    fun swipeDown() = apply { this.get().swipeDown() }
    fun swipeLeft() = apply { this.get().swipeLeft() }
    fun swipeRight() = apply { this.get().swipeRight() }
    fun swipeUp() = apply { this.get().swipeUp() }
    fun execute(action: ViewAction) = apply { this.get().execute(action) }

    //assertions
    fun isDisplayed() = apply { this.get().isDisplayed() }
    fun isCompletelyDisplayed() = apply { this.get().isCompletelyDisplayed() }
    fun isDisplayingAtLeast(percentage: Int) = apply { this.get().isDisplayingAtLeast(percentage) }
    fun isClickable() = apply { this.get().isClickable() }
    fun assertMatches(condition: Matcher<View>) = apply { this.get().assertMatches(condition) }

}