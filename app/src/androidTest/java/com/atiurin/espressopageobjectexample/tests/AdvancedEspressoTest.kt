package com.atiurin.espressopageobjectexample.tests

import android.content.Intent
import android.util.Log
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.atiurin.espressopageobject.extensions.*
import com.atiurin.espressopageobject.recyclerview.withRecyclerView
import com.atiurin.espressopageobjectexample.R
import com.atiurin.espressopageobjectexample.activity.MainActivity
import com.atiurin.espressopageobjectexample.data.repositories.CURRENT_USER
import com.atiurin.espressopageobjectexample.idlingresources.resources.ContactsIdlingResource
import com.atiurin.espressopageobjectexample.idlingresources.resources.ChatIdlingResource
import com.atiurin.espressopageobjectexample.managers.AccountManager
import com.atiurin.espressopageobjectexample.pages.ChatPage
import com.atiurin.espressopageobjectexample.pages.FriendsListPage
import org.junit.*

class AdvancedEspressoTest {
    private val idlingRes = ContactsIdlingResource.getInstanceFromTest()
    private val idlingRes2 = ChatIdlingResource.getInstanceFromTest()
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    companion object {

        @BeforeClass @JvmStatic
        fun beforeClass(){
            ViewActionsConfig.beforeAction = {
                Log.d("Espresso", "before clasee ${ViewActionsConfig.currentEspressoAction?.type?.name}")
            }

            ViewActionsConfig.afterAction = {
                Log.d("Espresso", "made action")
            }
        }
    }

    @Before
    fun registerResource() {
        AccountManager(getInstrumentation().targetContext).login(CURRENT_USER.login, CURRENT_USER.password)
        mActivityRule.launchActivity(Intent())
//        IdlingRegistry.getInstance().register(idlingRes,idlingRes2)

    }

    @Test
    fun advancedSendMessageWithSteps(){
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
                  .sendMessage("message2")
    }

    @Test
    fun friendsItemCheck(){
        FriendsListPage().assertName("Janice")
                         .assertStatus("Janice","Oh. My. God")
    }

    @Test
    fun advancedSendMessageWithPageObject() {
        val messageText = "message3"
        FriendsListPage().getListItem("Janice").click()
        val chatPage = ChatPage()
        chatPage.getTitle("Janice").isDisplayed()
        chatPage.openOptionsMenu()
        chatPage.clearHistoryBtn.click()
        chatPage.inputMessageText.typeText(messageText)
        chatPage.sendMessageBtn.click()
        chatPage.getListItem(messageText).text
            .isDisplayed()
            .hasText(messageText)
        Thread.sleep(1000)
    }

    @Test
    fun testRecyclerView(){
        withRecyclerView(withId(R.id.recycler_friends)).atItem(hasDescendant(withText("TO FAIL TEST"))).isDisplayed()
        Thread.sleep(2000)
    }

    @After
    fun unregisterResource() {
//        IdlingRegistry.getInstance().unregister(idlingRes)
    }


}