# Espresso page object

[![Download](https://api.bintray.com/packages/alex-tiurin/espresso-page-object/espressopageobject/images/download.svg)](https://bintray.com/alex-tiurin/espresso-page-object/espressopageobject/_latestVersion)

[![Android CI](https://github.com/alex-tiurin/espresso-page-object/workflows/Android CI/badge.svg)](https://github.com/alex-tiurin/espresso-page-object/actions)

This library provides access to nice and simple DSL for Espresso framework.
You don't need to learn any new classes or special syntax. All magic actions and assertions are provided from crunch.
Library can be easy customised and extended by advanced user. Wish you only stable tests!

## Russian README is [here](https://github.com/alex-tiurin/espresso-page-object/blob/master/README_RU.md)

## What are the benefits of using the library?

- Stability of all actions and assertions
- An architectural approach to writing tests
- Simple and straightforward syntax

The standard Espresso syntax is complex and not intuitive to understand. This is especially evident when interacting with the RecyclerView

Let's look at 2 examples:

1. Click on simple button.

Clear Espresso

```kotlin
onView(withId(R.id.send_button)).perform(click())
```
 Espresso page object
```kotlin
withId(R.id.send_button).click()
```

2. Click on RecyclerView list item

Clear Espresso

```kotlin
onView(withId(R.id.recycler_friends))
    .perform(
        RecyclerViewActions
            .actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Janice")),
                click()
            )
        )
```
 Espresso page object
```kotlin
withRecyclerView(withId(R.id.recycler_friends))
    .atItem(hasDescendant(withText("Janice")))
    .click()
```

## Features

-  [How to interact with RecyclerView](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/adapterview.md)
-  [AdapterView and onData](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/adapterview.md)
-  [How we reduce flakiness of all actions and assertions](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/operations_stability.md)
-  [Lifecycle listener. Listen all operations and their results.](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/lifecycle_listener.md)
-  [RuleSequence + SetUpTearDownRule. Full control under your tests](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/rulesequence_setupterdownrule.md)

## 3 steps to write a test using espresso-page-object

I try to advocate the correct construction of the test framework architecture, the division of responsibilities between the layers and other correct things.

Therefore, I would like to recommend the following approach when your are using the library.

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

Use RuleSequence + SetUpTearDownRule to prepare test data.

In general, it all comes down to the fact that the architecture of your project will look like this.

! [Architecture] (https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/img/architecture.png)

## Add ot you project
Gradle
```groovy
repositories {
    jcenter()
}

dependencies {
    androidTestImplementation 'com.atiurin.espresso:espressopageobject:0.1.16'
}
```
Maven
```
<dependency>
  <groupId>com.atiurin.espresso</groupId>
  <artifactId>espressopageobject</artifactId>
  <version>0.1.16</version>
  <type>pom</type>
</dependency>
```

## AndroidX

It is required to use AndroidX libraries. You can get some problems with Android Support ones.