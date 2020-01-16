package com.atiurin.espressopageobjectexample.tests

import android.content.Intent
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.testlifecycle.setupteardown.SetUpTearDownRule
import com.atiurin.espressopageobjectexample.activity.MainActivity
import com.atiurin.espressopageobjectexample.data.repositories.CURRENT_USER
import com.atiurin.espressopageobjectexample.framework.Log
import com.atiurin.espressopageobjectexample.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressopageobjectexample.managers.AccountManager
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class BaseTest{

    companion object{
        const val FREQUENT_SETUP_KEY = "frequent_setup_key"
    }
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()

    @get:Rule
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    open val setupRule = SetUpTearDownRule()
        .addSetUp (FREQUENT_SETUP_KEY){ Log.info("FREQUENT_SETUP_KEY setup executed first of all")  }

    @Before
    fun registerResource() {
        AccountManager(InstrumentationRegistry.getInstrumentation().targetContext).login(
            CURRENT_USER.login, CURRENT_USER.password)
        mActivityRule.launchActivity(Intent())
        IdlingRegistry.getInstance().register(idlingRes)
    }

    @After
    fun unregisterResource() {
        IdlingRegistry.getInstance().unregister(idlingRes)
    }
}