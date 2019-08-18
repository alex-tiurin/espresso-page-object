package com.atiurin.espressopageobject.extensions

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isJavascriptEnabled
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.view.View
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.afterAssertion
import com.atiurin.espressopageobject.extensions.ViewAssertionsConfig.beforeAssertion
import org.hamcrest.Matcher

private fun assertView(viewMatcher: Matcher<View>, condition: Matcher<View>) {
    beforeAssertion()
    onView(viewMatcher).check(matches(condition))
    afterAssertion()
}

fun Matcher<View>.isDisplayed() = apply {
    assertView(this, ViewMatchers.isDisplayed())
}

fun Matcher<View>.isCompletelyDisplayed() = apply {
    assertView(this, ViewMatchers.isCompletelyDisplayed())
}

fun Matcher<View>.isDisplayingAtLeast(percentage: Int) = apply {
    assertView(this, ViewMatchers.isDisplayingAtLeast(percentage))
}

fun Matcher<View>.isEnabled() = apply {
    assertView(this, ViewMatchers.isEnabled())
}

fun Matcher<View>.isSelected() = apply {
    assertView(this, ViewMatchers.isSelected())
}

fun Matcher<View>.isClickable() = apply {
    assertView(this, ViewMatchers.isClickable())
}

fun Matcher<View>.isChecked() = apply {
    assertView(this, ViewMatchers.isChecked())
}

fun Matcher<View>.isNotChecked() = apply {
    assertView(this, ViewMatchers.isNotChecked())
}

fun Matcher<View>.isFocusable() = apply {
    assertView(this, ViewMatchers.isFocusable())
}

fun Matcher<View>.isJavascriptEnabled() = apply {
    assertView(this, ViewMatchers.isJavascriptEnabled())
}

fun Matcher<View>.hasText(text: String) = apply {
    assertView(this, withText(text))
}

fun Matcher<View>.assertMatches(condition: Matcher<View>) = apply {
    assertView(this, condition)
}

object ViewAssertionsConfig{
    var beforeAssertion= {}
    var afterAssertion= {}
}

