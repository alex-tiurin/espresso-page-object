package com.atiurin.espressopageobject.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import com.atiurin.espressopageobject.extensions.*
import org.hamcrest.Matcher

open class RecyclerViewItem {
    private var recyclerViewMatcher: Matcher<View>
    private var autoScroll: Boolean = true
    private var itemViewMatcher: Matcher<View>? = null
    private var position: Int = -1

    constructor(
        recyclerViewMatcher: Matcher<View>,
        itemViewMatcher: Matcher<View>,
        autoScroll: Boolean = true
    ) {
        this.recyclerViewMatcher = recyclerViewMatcher
        this.itemViewMatcher = itemViewMatcher
        this.autoScroll = autoScroll
        if (autoScroll) {
            scrollToItem()
        }
    }

    constructor(recyclerViewMatcher: Matcher<View>, position: Int, autoScroll: Boolean = true) {
        if (position < 0) throw Exception("Position param value can't be negative!")
        this.recyclerViewMatcher = recyclerViewMatcher
        this.position = position
        this.autoScroll = autoScroll
        if (autoScroll) {
            scrollToItem()
        }
    }

    //TODO work around this trade off
    fun scrollToItem(): RecyclerViewItem = apply {
        if (position < 0) {
            if (itemViewMatcher == null) {
                throw Exception("You need to specify itemMatcher param!")
            }
            recyclerViewMatcher.execute(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(itemViewMatcher)
            )
        } else {
            recyclerViewMatcher.execute(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position)
            )
        }

    }

    private fun get(): Matcher<View> {
        return if (position < 0) {
            if (itemViewMatcher == null) {
                throw Exception("You need to specify itemMatcher param!")
            }
            withRecyclerView(recyclerViewMatcher).atItem(itemViewMatcher!!)
        } else withRecyclerView(recyclerViewMatcher).atPosition(position)
    }

    fun getChildMatcher(childMatcher: Matcher<View>): Matcher<View> {
        return if (position < 0) {
            if (itemViewMatcher == null) {
                throw Exception("You need to specify itemMatcher param!")
            }
            withRecyclerView(recyclerViewMatcher).atItemChild(itemViewMatcher!!, childMatcher)
        } else withRecyclerView(recyclerViewMatcher).atPositionItemChild(position, childMatcher)
    }

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