package com.atiurin.espressopageobjectexample.tests

import android.os.SystemClock
import android.view.KeyEvent
import androidx.test.espresso.action.EspressoKey
import androidx.test.espresso.action.ViewActions.click
import com.atiurin.espressopageobject.extensions.*
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.framework.utils.AssertUtils
import com.atiurin.espressopageobjectexample.framework.utils.TestDataUtils.getResourceString
import org.junit.Test

class ViewInteractionActionsTest : UiElementsTest() {
    @Test
    fun click_onClickable() {
        page.button.click()
        page.eventStatus.containsText(getResourceString(R.string.button_event_click))
    }

    @Test
    fun longClick_onLongClickable() {
        page.button.longClick()
        page.eventStatus.containsText(getResourceString(R.string.button_event_long_click))
    }

    @Test
    fun doubleClick_onClickable() {
        page.button.doubleClick()
        page.eventStatus
            .containsText(getResourceString(R.string.button_event_click))
            .containsText("2")
    }

    @Test
    fun typeText_onEditable() {
        val text1 = "begin"
        val text2 = "simple text"
        page.editTextContentDesc.replaceText(text1).typeText(text2).hasText("$text1$text2")
    }

    @Test
    fun typeText_onNotEditable() {
        AssertUtils.assertException { page.eventStatus.typeText("simple text", 1000) }
    }

    @Test
    fun replaceText_onEditable() {
        val text = "simple text"
        page.editTextContentDesc.replaceText(text).hasText(text)
    }

    @Test
    fun clearText_onEditable() {
        page.editTextContentDesc.clearText().hasText("")
    }

    @Test
    fun pressKey_onEditable() {
        val text = "simple text"
        val expectedText = text.substring(0, text.length - 1)
        page.editTextContentDesc
            .replaceText(text)
            .click()
            .pressKey(KeyEvent.KEYCODE_DEL)
            .hasText(expectedText)
    }

    @Test
    fun pressEspressoKey_onEditable() {
        val text = "simple text"
        val expectedText = text.substring(0, text.length - 1)
        page.editTextContentDesc
            .replaceText(text)
            .click()
            .pressKey(EspressoKey.Builder().withKeyCode(KeyEvent.KEYCODE_DEL).build())
            .hasText(expectedText)
    }

    @Test
    fun closeSoftKeyboard_whenItOpened(){
        page.editTextContentDesc.click()
        SystemClock.sleep(500)
        page.editTextContentDesc.closeSoftKeyboard()
    }

    @Test
    fun executeCustomClick_onClickable(){
        page.button.execute(click())
        page.eventStatus.containsText(getResourceString(R.string.button_event_click))
    }
}