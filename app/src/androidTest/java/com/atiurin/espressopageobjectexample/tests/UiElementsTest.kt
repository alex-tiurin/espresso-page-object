package com.atiurin.espressopageobjectexample.tests

import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobjectexample.activity.UiElementsActivity
import com.atiurin.espressopageobjectexample.pages.UiElementsPage

abstract class UiElementsTest : BaseTest() {
    val activityRule = ActivityTestRule(UiElementsActivity::class.java)

    init {
        ruleSequence.add(activityRule)
    }

    val page = UiElementsPage()
}