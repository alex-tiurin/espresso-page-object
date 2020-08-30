# Espresso page object

[![Download](https://api.bintray.com/packages/alex-tiurin/espresso-page-object/espressopageobject/images/download.svg)](https://bintray.com/alex-tiurin/espresso-page-object/espressopageobject/_latestVersion)

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


## Features

-  [How to interact with RecyclerView](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/adapterview.md)
-  [AdapterView and onData](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/adapterview.md)
-  [How we reduce flakiness of all actions and assertions](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/operations_stability.md)
-  [Lifecycle listener. Listen all operations and their results.](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/lifecycle_listener.md)
-  [RuleSequence + SetUpTearDownRule. Full control under your tests](https://github.com/alex-tiurin/espresso-page-object/blob/master/wiki/en/rulesequence_setupterdownrule.md)

