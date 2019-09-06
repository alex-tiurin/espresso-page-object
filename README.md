# Espresso page object
## Подключение к проекту
Gradle
```groovy
repositories {
    jcenter()
}
    
dependencies {
    androidTestImplementation 'com.atiurin.espresso:espressopageobject:0.1.3'
}
```
Maven
```
<dependency>
  <groupId>com.atiurin.espresso</groupId>
  <artifactId>espressopageobject</artifactId>
  <version>0.1.3</version>
  <type>pom</type>
</dependency>
```

## AndroidX

Необходимо, чтобы ваш проект использовал AndroidX библиотеки. С android support могут возникнуть проблемы.

## 3 шага для написания теста с использованием espresso-page-object

1. Создайте PageObject class и определите Matcher<View> UI элементов экрана в нем

```kotlin
class ChatPage : Page {
    private val messagesList = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)
}
```
Некоторые элементы, такие как item списков, могут вычисляться динамически, в зависимости от данных приложения.
Тогда для их определния в класс PageObject необходимо добавить метод, возвращающий объект Matcher<View>
```kotlin
class ChatPage : Page {
    private fun getName(name: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(name))
    }
}
```

2. Добавьте методы действий пользоватя в класс PageObject

```kotlin
class ChatPage : Page {
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
См. полный код [ChatPage.class](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/src/androidTest/java/com/atiurin/espressopageobjectexample/pages/ChatPage.kt)

3. Добаь действия пользователя в тест

```kotlin
    @Test
    fun friendsItemCheck(){
        FriendsListPage()
            .assertName("Janice")
            .assertStatus("Janice","Oh. My. God")
    }
    @Test
    fun sendMessage(){
        FriendsListPage().openChat("Janice")
        ChatPage().clearHistory()
            .sendMessage("test message")
    }
```

См. полный код с примером теста [DemoEspressoTest](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/src/androidTest/java/com/atiurin/espressopageobjectexample/tests/DemoEspressoTest.kt)