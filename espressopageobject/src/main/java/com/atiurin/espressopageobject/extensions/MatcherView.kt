package com.atiurin.espressopageobject.extensions

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.EspressoKey
import org.hamcrest.Matcher


fun Matcher<View>.click() = apply {
    onView(this).click()
}

fun Matcher<View>.doubleClick() = apply {
    onView(this).doubleClick()
}

fun Matcher<View>.longClick() = apply {
    onView(this).longClick()
}

fun Matcher<View>.typeText(text: String) = apply {
    onView(this).typeText(text)
}

fun Matcher<View>.replaceText(text: String) = apply {
    onView(this).replaceText(text)
}

fun Matcher<View>.clearText() = apply {
    onView(this).clearText()
}

fun Matcher<View>.pressKey(keyCode: Int) = apply {
    onView(this).pressKey(keyCode)
}

fun Matcher<View>.pressKey(key: EspressoKey) = apply {
    onView(this).pressKey(key)
}

fun Matcher<View>.closeSoftKeyboard() = apply {
    onView(this).closeSoftKeyboard()
}

fun Matcher<View>.swipeLeft() = apply {
    onView(this).swipeLeft()
}

fun Matcher<View>.swipeRight() = apply {
    onView(this).swipeRight()
}

fun Matcher<View>.swipeUp() = apply {
    onView(this).swipeUp()
}

fun Matcher<View>.swipeDown() = apply {
    onView(this).swipeDown()
}

fun Matcher<View>.scrollTo() = apply {
    onView(this).scrollTo()
}

fun Matcher<View>.process(viewAction: ViewAction) {
    onView(this).process(viewAction)
}
//assertions


fun Matcher<View>.isDisplayed() = apply {
    onView(this).isDisplayed()
}

fun Matcher<View>.isCompletelyDisplayed() = apply {
    onView(this).isCompletelyDisplayed()
}

fun Matcher<View>.isDisplayingAtLeast(percentage: Int) = apply {
    onView(this).isDisplayingAtLeast(percentage)
}

fun Matcher<View>.isEnabled() = apply {
    onView(this).isEnabled()
}

fun Matcher<View>.isSelected() = apply {
    onView(this).isSelected()
}

fun Matcher<View>.isClickable() = apply {
    onView(this).isClickable()
}

fun Matcher<View>.isChecked() = apply {
    onView(this).isChecked()
}

fun Matcher<View>.isNotChecked() = apply {
    onView(this).isNotChecked()
}

fun Matcher<View>.isFocusable() = apply {
    onView(this).isFocusable()
}

fun Matcher<View>.isJavascriptEnabled() = apply {
    onView(this).isJavascriptEnabled()
}

fun Matcher<View>.hasText(text: String) = apply {
    onView(this).hasText(text)
}

fun Matcher<View>.assertMatches(condition: Matcher<View>) = apply {
    onView(this).assertMatches(condition)
}