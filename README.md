# Espresso page object

Это библиотека, предоставляющая доступ к простому и понятному  DSL для работы с Espresso фреймворком.
Вам не нужно запоминать новые классы, изучать новый синтаксис. Все действия у вас появляются из коробки.
Для продвинутых пользователей библиотека предоставляет хорошую возможность в кастомизации и расширении возможностей DSL.
Стабильных Вам тестов!

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
class ChatPage {
    private val messagesList = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)
}
```
Некоторые элементы, такие как заголовки открытого чата, могут вычисляться динамически, в зависимости от данных приложения.
Тогда для их определния в класс PageObject необходимо добавить метод, возвращающий объект Matcher<View>
```kotlin
class ChatPage {
    private fun getTitle(title: String): Matcher<View> {
        return allOf(withId(R.id.toolbar_title), withText(title))
    }
}
```

2. Добавьте методы действий пользоватя в класс PageObject

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
См. полный код [ChatPage.class](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/src/androidTest/java/com/atiurin/espressopageobjectexample/pages/ChatPage.kt)

3. Добавьте действия пользователя в тест

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

## Работа с RecyclerView
Прежде чем начать, определимся с понятиями:
- RecyclerView - список элементов (есть стандартный класс RecyclerView в Android фреймворке)
- RecyclerViewItem - один из элементов списка (есть класс RecyclerViewItem в библиотеке espresso-page-object)
- RecyclerItemChild - дочерний элемент внутри элемента списка (просто понятие, отдельного класса для работы с дочерними элементами нет)

![RecyclerViewItem](https://github.com/alex-tiurin/espresso-page-object/blob/master/app/img/recyclerViewItem.png)

Если необходимо работать только с элементами списка и нет нужды работать и проверять его дочерние элементы, то создаем метод, который возвращает экземпляр класса RecyclerView

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

Если необходимо работать с дочерними элементами списка, то создаем наследника RecyclerViewItem и описываем дочерние элементы:

```kotlin
class FriendsListPage : Page {
    val friendsList = withId(R.id.recycler_friends)

    class FriendRecyclerItem(list: Matcher<View>, item: Matcher<View>) : RecyclerViewItem(list, item) {
        val name = getChildMatcher(withId(R.id.tv_name))
        val status = getChildMatcher(withId(R.id.tv_status))
    }

    fun getListItem(title: String): FriendRecyclerItem {
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
Обратите внимание, что дочерние элементы item создаются с использованием метода **getChildMatcher**
```kotlin
val name = getChildMatcher(withId(R.id.tv_name))
```
## Особенности

### onData(Matcher<View>)
Если нужно работать с AdapterView и использовать onData(Matcher<View>)
```kotlin

class SomePage{
    val adapterElement = onData(withText(R.id.textId))
}
```
Все функции click(), longClick(), isDisplayed() и т.д. будут доступны

### Все действия выполняются через failureHandler

Как это работает? В течении заданного тайм-аута (по умолчанию 5 секунд), при выполнении действия будут перехватываться 2 exception%
- PerformException
- NoMatchingViewException

Действие будет повторяться каждые 50мс, пока не выполниться или не достигнет тайм-аут. 

Такой подход позволяет снизить flakiness ваших тестов и поднять их стабильность.

Вы можете отключить подобное поведение добавив перед тестом строки
```kotlin
ViewActionsConfig.allowedExceptions.clear()     
ViewAssertionsConfig.allowedExceptions.clear() 
```
Можете расширить список обрабатываемых исключений:
```kotlin
ViewActionsConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
ViewAssertionsConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
```
Можете поменять время тайм-аута действия:
```kotlin
ViewActionsConfig.ACTION_TIMEOUT = 10000L
ViewAssertionsConfig.ASSERTION_TIMEOUT = 10000L
```
