package com.atiurin.espressopageobject.extensions

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.EspressoKey
import android.support.test.espresso.action.ViewActions
import android.view.View
import com.atiurin.espressopageobject.extensions.ViewActionsConfig.afterAction
import com.atiurin.espressopageobject.extensions.ViewActionsConfig.beforeAction
import org.hamcrest.Matcher

private fun actionOnView(viewMatcher: Matcher<View>, action: ViewAction){
    beforeAction()
    onView(viewMatcher).perform(action)
    afterAction()
}

fun Matcher<View>.click() = apply {
    actionOnView(this, ViewActions.click())
}

fun Matcher<View>.doubleClick() = apply {
    actionOnView(this, ViewActions.doubleClick())
}

fun Matcher<View>.longClick() = apply {
    actionOnView(this, ViewActions.longClick())
}

fun Matcher<View>.typeText(text: String) = apply {
    actionOnView(this, ViewActions.typeText(text))
}

fun Matcher<View>.replaceText(text: String) = apply {
    actionOnView(this, ViewActions.replaceText(text))
}

fun Matcher<View>.clearText() = apply {
    actionOnView(this, ViewActions.clearText())
}

fun Matcher<View>.pressKey(keyCode: Int) = apply {
    actionOnView(this, ViewActions.pressKey(keyCode))
}

fun Matcher<View>.pressKey(key: EspressoKey) = apply {
    actionOnView(this, ViewActions.pressKey(key))
}

fun Matcher<View>.closeSoftKeyboard() = apply {
    actionOnView(this, ViewActions.closeSoftKeyboard())
}

fun Matcher<View>.swipeLeft() = apply {
    actionOnView(this, ViewActions.swipeLeft())
}

fun Matcher<View>.swipeRight() = apply {
    actionOnView(this, ViewActions.swipeRight())
}

fun Matcher<View>.swipeUp() = apply {
    actionOnView(this, ViewActions.swipeUp())
}

fun Matcher<View>.swipeDown() = apply {
    actionOnView(this, ViewActions.swipeDown())
}

fun Matcher<View>.scrollTo() = apply {
    actionOnView(this, ViewActions.scrollTo())
}

fun Matcher<View>.execute(action: ViewAction){
    actionOnView(this, action)
}

object ViewActionsConfig{
    var beforeAction= {}
    var afterAction= {}
}
