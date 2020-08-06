package com.atiurin.espressopageobjectexample.tests

import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.extensions.*
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.activity.UiElementsActivity
import com.atiurin.espressopageobjectexample.framework.utils.AssetUtils
import com.atiurin.espressopageobjectexample.framework.utils.TestDataUtils.getResourceString
import com.atiurin.espressopageobjectexample.pages.UiElementsPage
import org.hamcrest.Matchers.*
import org.junit.Test

class UiElementsTestAssertions : BaseTest() {
    val activityRule = ActivityTestRule(UiElementsActivity::class.java)

    init {
        ruleSequence.add(activityRule)
    }

    val page = UiElementsPage()

    //displayed
    @Test
    fun isDisplayed_ofDisplayedObject() {
        page.button.isDisplayed()
    }

    @Test
    fun isDisplayed_ofNotDisplayedObject() {
        page.radioInvisibleButton.click()
        AssetUtils.assertException { page.button.isDisplayed(1000) }
    }

    @Test
    fun isNotDisplayed_ofDisplayedObject() {
        page.radioVisibleButton.click()
        AssetUtils.assertException { page.button.isNotDisplayed(1000) }
    }

    @Test
    fun isNotDisplayed_ofNotDisplayedObject() {
        page.radioInvisibleButton.click()
        page.button.isNotDisplayed()
    }
    //checked
    @Test
    fun isChecked_ofChecked() {
        page.checkBoxEnabled.isChecked()
    }

    @Test
    fun isChecked_ofNotChecked() {
        AssetUtils.assertException { page.checkBoxSelected.isChecked(1000) }
    }

    @Test
    fun isNotChecked_ofChecked() {
        AssetUtils.assertException { page.checkBoxClickable.isNotChecked(1000) }
    }

    @Test
    fun isNotChecked_ofNotChecked() {
        page.checkBoxSelected.isNotChecked()
    }
    // selected
    @Test
    fun isSelected_ofSelected() {
        page.checkBoxSelected.click()
        page.button.isSelected()
    }

    @Test
    fun isSelected_ofNotSelected() {
        AssetUtils.assertException { page.button.isSelected(1000) }
    }

    @Test
    fun isNotSelected_ofSelected() {
        page.checkBoxSelected.click()
        AssetUtils.assertException { page.button.isNotSelected(1000) }
    }

    @Test
    fun isNotSelected_ofNotSelected() {
        page.button.isNotSelected()
    }
    // enabled
    @Test
    fun isEnabled_ofEnabled() {
        page.button.isEnabled()
    }

    @Test
    fun isEnabled_ofNotEnabled() {
        page.checkBoxEnabled.click()
        AssetUtils.assertException { page.button.isEnabled(1000) }
    }

    @Test
    fun isNotEnabled_ofEnabled() {
        AssetUtils.assertException { page.button.isNotEnabled(1000) }
    }

    @Test
    fun isNotEnabled_ofNotEnabled() {
        page.checkBoxEnabled.click()
        page.button.isNotEnabled()
    }
    //clickable
    @Test
    fun isClickable_ofClickable() {
        page.button.isClickable()
    }

    @Test
    fun isClickable_ofNotClickable() {
        page.checkBoxClickable.click()
        AssetUtils.assertException { page.button.isClickable(1000) }
    }

    @Test
    fun isNotClickable_ofClickable() {
        AssetUtils.assertException { page.button.isNotClickable(1000) }
    }

    @Test
    fun isNotClickable_ofNotClickable() {
        page.checkBoxClickable.click()
        page.button.isNotClickable()
    }
    //focusable
    @Test
    fun isFocusable_ofFocusable() {
        page.button.isFocusable()
    }

    @Test
    fun isFocusable_ofNotFocusable() {
        page.checkBoxFocusable.click()
        AssetUtils.assertException { page.button.isFocusable(1000) }
    }

    @Test
    fun isNotFocusable_ofFocusable() {
        AssetUtils.assertException {  page.button.isNotFocusable(1000) }
    }

    @Test
    fun isNotFocusable_ofNotFocusable() {
        page.checkBoxFocusable.click()
        page.button.isNotFocusable()
    }
    //hasFocus
    @Test
    fun hasFocus_ofFocused() {
        page.editTextContentDesc.click()
        page.editTextContentDesc.hasFocus()
    }

    @Test
    fun hasFocus_ofNotFocused() {
        AssetUtils.assertException { page.editTextContentDesc.hasFocus(1000) }
    }
    //hasText
    @Test
    fun hasText_CorrectText_withResourceId(){
        page.editTextContentDesc.hasText(R.string.button_default_content_desc)
    }

    @Test
    fun hasText_InvalidSubstringText(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.editTextContentDesc.hasText(text.substring(3), 1000) }
    }

    @Test
    fun hasText_InvalidText_withResourceId(){
        AssetUtils.assertException { page.editTextContentDesc.hasText(R.string.action_clear_history, 1000) }
    }

    @Test
    fun hasText_CorrectText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        page.editTextContentDesc.hasText(text)
    }

    @Test
    fun hasText_InvalidText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.editTextContentDesc.hasText("$text to be invalid", 1000) }
    }

    @Test
    fun hasText_CorrectText_withStringMatcher(){
        val text = getResourceString(R.string.button_default_content_desc)
        page.editTextContentDesc.hasText(containsString(text.substring(2)))
    }

    @Test
    fun hasText_InvalidText_withStringMatcher(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.editTextContentDesc.hasText(containsString("$text to be invalid"), 1000) }
    }
    //containsText
    @Test
    fun containsText_CorrectText_withResourceId(){
        val text = getResourceString(R.string.button_default_content_desc)
        page.editTextContentDesc.containsText(text.substring(3))
    }

    @Test
    fun containsText_InvalidSubstringText(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.editTextContentDesc.containsText("${text.substring(3)} to be invalid", 1000) }
    }

    //hasContentDescription
    @Test
    fun hasContentDescription_CorrectText_withResourceId(){
        page.button.hasContentDescription(R.string.button_default_content_desc)
    }

    @Test
    fun hasContentDescription_InvalidSubstringText(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.button.hasContentDescription(text.substring(3), 1000) }
    }

    @Test
    fun hasContentDescription_InvalidText_withResourceId(){
        AssetUtils.assertException { page.button.hasContentDescription(R.string.action_clear_history, 1000) }
    }

    @Test
    fun hasContentDescription_CorrectText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        page.button.hasContentDescription(text)
    }

    @Test
    fun hasContentDescription_InvalidText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.button.hasContentDescription("$text to be invalid", 1000) }
    }

    //contentDescriptionContains
    @Test
    fun contentDescriptionContains_CorrectText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        page.button.contentDescriptionContains(text.substring(2), 1000)
    }
    @Test
    fun contentDescriptionContains_InvalidText_withString(){
        val text = getResourceString(R.string.button_default_content_desc)
        AssetUtils.assertException { page.button.contentDescriptionContains("${text.substring(2)} to be invalid", 1000) }
    }

    //assertMatches
    @Test
    fun assertMatches_ofMatched(){
        page.button.assertMatches(allOf(isDisplayed(), isEnabled(), withText(R.string.button_text)))
    }

    @Test
    fun assertMatches_ofNotMatched(){
        page.checkBoxEnabled.click()
        AssetUtils.assertException { page.button.assertMatches(allOf(isDisplayed(), isEnabled(), withText(R.string.button_text)), 1000) }
    }

    //javascripEnabled
    @Test
    fun jsEnabled_ofEnabled(){
        page.webView.isJavascriptEnabled()
    }

    @Test
    fun jsEnabled_ofNotEnabled(){
        page.checkBoxJsEnabled.click()
        AssetUtils.assertException { page.webView.isJavascriptEnabled(1000) }
    }
}