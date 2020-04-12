package com.atiurin.espressopageobjectexample.tests

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.testlifecycle.rulesequence.RuleSequence
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import com.atiurin.espressopageobjectexample.activity.MainActivity
import com.atiurin.espressopageobjectexample.data.repositories.CURRENT_USER
import com.atiurin.espressopageobjectexample.framework.Log
import com.atiurin.espressopageobjectexample.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressopageobjectexample.managers.AccountManager
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest {

    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()

    val setupRule = SetUpTearDownRule()
        .addSetUp {
            Log.info("Login valid user")
            AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
                CURRENT_USER.login, CURRENT_USER.password
            )
        }.addSetUp {
            Log.info("Register idling resource")
            IdlingRegistry.getInstance().register(idlingRes)
        }.addTearDown {
            Log.info("Unregister idling resource")
            IdlingRegistry.getInstance().unregister(idlingRes)
        }

    @get:Rule
    open val ruleSequence = RuleSequence(setupRule)
}