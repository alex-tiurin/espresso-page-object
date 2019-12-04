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

Необходимо, чтобы ваш проект использовал AndroidX библиотеки. С android support могут возникнуть проблемы.

## 3 шага для написания теста с использованием espresso-page-object

1. Создайте PageObject class и определите `Matcher<View>` UI элементов экрана в нем

```kotlin
class ChatPage {
    private val messagesList = withId(R.id.messages_list)
    private val clearHistoryBtn = withText("Clear history")
    private val inputMessageText = withId(R.id.message_input_text)
    private val sendMessageBtn = withId(R.id.send_button)
}
```
Некоторые элементы, такие как заголовки открытого чата, могут вычисляться динамически, в зависимости от данных приложения.
Тогда для их определния в класс PageObject необходимо добавить метод, возвращающий объект `Matcher<View>`
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
Обратите внимание, что дочерние элементы item создаются с использованием метода `getChildMatcher`
```kotlin
val name = getChildMatcher(withId(R.id.tv_name))
```
### Получение RecyclerViewItem по его позиции в списке

Иногда, получить RecyclerViewItem и его дочерние элементы необходимо основываясь на позиции элемента в списке. Например, вам необходми получить первый элементв в списке.

Для этого необходимо использовать другой конструктор класса RecyclerViewItem.

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

## Особенности библиотеки

### `onData(Matcher<View>)`
Если нужно работать с AdapterView и использовать `onData(Matcher<View>)`
```kotlin

class SomePage{
    val adapterElement = onData(withText(R.id.textId))
}
```
Все функции click(), longClick(), isDisplayed() и т.д. будут доступны для `adapterElement`

### Все действия выполняются через failureHandler

Как это работает? В течении заданного тайм-аута (по умолчанию 5 секунд), при выполнении действия будут перехватываться 2 исключения
- PerformException
- NoMatchingViewException

Действие будет повторяться каждые 50мс, пока не выполниться успешно или не достигнется тайм-аут. 

Такой подход позволяет снизить flakiness ваших тестов и поднять их стабильность.

Вы можете отключить подобное поведение добавив перед тестом строки
```kotlin
ViewActionConfig.allowedExceptions.clear()// disable failure handler
ViewAssertionConfig.allowedExceptions.clear()// disable failure handler
```
Можете расширить список обрабатываемых исключений:
```kotlin
ViewActionConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
ViewAssertionConfig.allowedExceptions.add(AmbiguousViewMatcherException::class.java)
```
Можете поменять время тайм-аута действия и проверки:
```kotlin
ViewActionConfig.ACTION_TIMEOUT = 10_000L
ViewAssertionConfig.ASSERTION_TIMEOUT = 10_000L
```

### Lifecycle Listeners

В библиотеке есть интерфейс, который позволяет прослушивать все действия
и проверки в момент их выполнения. Для этого интерфейс предоставляет 4
метода.

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

Для того, чтобы воспользоваться этим интерфейсом, вам необходимо создать
наследника абстрактного класса `AbstractLifecycleListener`. Например,
создадим листенер, который будет делать скриншот в случае неуспешного
выполнения действия или проверки.
```kotlin
class ScreenshotLifecycleListener : AbstractLifecycleListener(){
    override fun afterFailure(description: Description, throwable: Throwable) {
        takeScreenshot(description.type.toString()) // takeScreenshot() isn't implemented
    }
}
```

Теперь, созданный listener необходимо добавить в список используемых.
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

По умолчанию в список используемых listeners для действий и проверок
добавлен `LogLifecycleListener`. 

Если вы хотите сбросить список используемых listeners, то необходимо
сделать следующее:

```kotlin
ViewActionLifecycle.clearListeners()
ViewAssertionLifecycle.clearListeners()
```

*Обратить внимание, что тяжелый listener может снизить скорость
выполнения ваших тестов!*