# Espresso page object

This library provides access to nice and simple DSL for Espresso framework.
You don't need to learn any new classes or special syntax. All magic actions and assertions are provided from crunch.
Library can be easy customised and extended by advanced user. Wish you only stable tests!

## Russian README is [here](https://github.com/alex-tiurin/espresso-page-object/blob/master/README_RU.md)

## Add ot you project
Gradle
```groovy
repositories {
    jcenter()
}
    
dependencies {
    androidTestImplementation 'com.atiurin.espresso:espressopageobject:0.1.13'
}
```
Maven
```
<dependency>
  <groupId>com.atiurin.espresso</groupId>
  <artifactId>espressopageobject</artifactId>
  <version>0.1.13</version>
  <type>pom</type>
</dependency>
```

## AndroidX

It is required to use AndroidX libraries. You can get some problems with Android Support ones.

## 3 steps to write a test using espresso-page-object

1. Create a PageObject class and specify screen UI elements `Matcher<View>`

```kotlin
class ChatPage {
    private val messagesList = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)
}
```
Some elements like chat title could be determined dynamically with application data.
In this case you need to add a method in PageObject class which will return `Matcher<View>` object.

```kotlin
class ChatPage {
    private fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }
}
```

2. Describe user step methods in PageObject class.

```kotlin
class ChatPage {
    fun sendMessage(text: String) = apply {
        inputMessageText.typeText(text)
        sendMessageBtn.click()
        this.getListItem(text).text
            .isDisplayed()
            .hasText(text)
    }

    fun clearHistory() = apply {
        openOptionsMenu()
        clearHistoryBtn.click()
    }
}
```
Full code sample [ChatPage.class](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/src/androidTest/java/com/atiurin/espressopageobjectexample/pages/ChatPage.kt)

3. Call user steps in test

```kotlin
    @Test
    fun friendsItemCheck(){
        FriendsListPage {
            assertName("Janice")
            assertStatus("Janice","Oh. My. God")
        }
    }
    @Test
    fun sendMessage(){
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage("test message")
    }
```

Full code sample [DemoEspressoTest](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/src/androidTest/java/com/atiurin/espressopageobjectexample/tests/DemoEspressoTest.kt)

## Interaction with RecyclerView
Before we go forward we need to define some terms:
- RecyclerView - list of some items (a standard Android framework class)
- RecyclerViewItem - single item of RecyclerView list (there is a class RecyclerViewItem in espresso-page-object lib)
- RecyclerItemChild - child element of RecyclerViewItem (just a term, there is no special class to work with child elements)

![RecyclerViewItem](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/img/recyclerViewItem.png)

In case you don't need to work with RecyclerItemChild you just need to create a method that returns RecyclerViewItem instance.

```kotlin
fun getListItem(text: String): RecyclerViewItem {
    return RecyclerViewItem(
        withId(R.id.recycler_friends),
        hasDescendant(allOf(withId(R.id.tv_name),withText(text)))
    )
}

fun someUserAction(): SomePage = apply{
    getListItem(text).longClick()
    ...
}
```

In case you need to make some actions or assertions on RecyclerItemChild you need to do the following:
- create a RecyclerViewItem subclass and describe a property of this subclass that represents a RecyclerItemChild,
- create a method that returns RecyclerViewItem subclass instance.

```kotlin
class FriendsListPage : Page {
    private val friendsList = withId(R.id.recycler_friends)

    class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>) : RecyclerViewItem(list, item) {
        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
    }

    private fun getListItem(title: String): FriendRecyclerItem {
        return FriendRecyclerItem(
            withId(R.id.recycler_friends),
            hasDescendant(allOf(withId(R.id.tv_name),withText(title)))
        )
    }

    fun assertStatus(name: String, status: String) = apply {
        step("Assert friend with name '$name' has status '$status'") {
            this.getListItem(name).status.hasText(status)
        }
    }
}
```
Note that RecyclerItemChild element is created by `getChildMatcher` method of RecyclerViewItem.class

```kotlin
val name = getChildMatcher(withId(R.id.tv_name))
```

### Positionable RecyclerViewItem 

Sometimes you need to get RecyclerViewItem by it's position in RecyclerView list. For example, you need to take a first item in a list.

In this case you need to use another constructor of RecyclerViewItem.class

```kotlin
class ChatPage : Page {
    private val messagesList = withId(R.id.messages_list)
    
    class ChatRecyclerItem : RecyclerViewItem {
        constructor(list: Matcher<View>, item: Matcher<View>) : super(list, item)
        constructor(list: Matcher<View>, position: Int) : super(list, position)

        val text = getChildMatcher(withId(R.id.message_text))
    }
    
    private fun getListItemAtPosition(position: Int): ChatRecyclerItem {
        return ChatRecyclerItem(messagesList, position)
    }
    
    fun assertMessageTextAtPosition(position: Int, text: String) = apply {
        this.getListItemAtPosition(position).text.isDisplayed().hasText(text)
    }
}
```

## Features

### `onData(Matcher<View>)`

In case you need to interact with AdapterView and use standard Espresso `onData(Matcher<View>)` method

```kotlin

class SomePage{
    val adapterElement = onData(withText(R.id.textId))
}
```

All actions and assertions like click(), longClick(), isDisplayed() and so on will be allowed for `adapterElement`

### All actions and assertions are executed with failureHandler

How does it work? Two exceptions will be caught during timeout (by default 5 seconds) 
- PerformException
- NoMatchingViewException

Action/assertion will be repeated every 50ms while it won't be successfully executed or timeout will be reached.

This approach allows us to reduce test flakiness.

It is possible to turn off this logic by adding next lines before test:

```kotlin
ViewActionConfig.allowedExceptions.clear()// disable failure handler
ViewAssertionConfig.allowedExceptions.clear()// disable failure handler
```

You can extend the list of caught exceptions:
```kotlin
ViewActionConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
ViewAssertionConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
```
You can change the timeout value for actions and assertions:
```kotlin
ViewActionConfig.ACTION_TIMEOUT = 10_000L
ViewAssertionConfig.ASSERTION_TIMEOUT = 10_000L
```
### Lifecycle Listeners

There is an interface to listen all actions and assertions in your test. 
It provides 4 methods to catch that's happening while operation execution.

```kotlin
LifecycleListener{
    /**
     * executed before any action or assertion
     */
    fun before(description: Description)
    /**
     * called when action or assertion has been executed successfully
     */
    fun afterSuccess(description: Description)

    /**
     * called when action or assertion failed
     */
    fun afterFailure(description: Description, throwable: Throwable)

    /**
     * called in any case of action or assertion result
     */
    fun after(description: Description)
    ...
}
```
To use this interface you need to create a child class of `AbstractLifecycleListener`. 
For example, let's create screenshot listener which will make a screenshot in case of action or assertion failure.
```kotlin
class ScreenshotLifecycleListener : AbstractLifecycleListener(){
    override fun afterFailure(description: Description, throwable: Throwable) {
        takeScreenshot(description.type.toString()) // takeScreenshot() isn't implemented
    }
}
```
To add new listener to lifecycle:
```kotlin
companion object {
    @BeforeClass @JvmStatic
    fun beforeClass() {
        val listener = ScreenshotLifecycleListener()
        ViewActionLifecycle.addListener(listener)
        ViewAssertionLifecycle.addListener(listener)
    }
}
```

There is an implementation of a `LogLifecycleListener` and it has been
added to ViewActionLifecycle and ViewAssertionLifecycle listeners by
default.

If you want to drop the list of listeners use:
```kotlin
ViewActionLifecycle.clearListeners()
ViewAssertionLifecycle.clearListeners()
```

*Note that heavy listeners could slow down your tests speed!*
