package com.atiurin.espressopageobject.testlifecycle.setupteardown

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.util.concurrent.atomic.AtomicInteger

/**
 * Class to execute setup and teardown methods before and after @Test
 *
 * How it works:
 * The execution order of all lambdas depends on it addition order
 * If lambda added without key it is executed for all @Tests in class
 * One lambda without key for SetUp and one for TearDown . The second one will override the first.
 */
open class SetUpTearDownRule : TestWatcher() {
    companion object {
        private const val ALL_SETUP_KEY = "ALL_SETUP_KEY"
    }

    private val setUpsCounter = AtomicInteger(0)
    private val tearDownsCounter = AtomicInteger(0)
    private val setUps = mutableListOf<Condition>()
    private val tearDowns = mutableListOf<Condition>()

    open fun addSetUp(key: String = ALL_SETUP_KEY, actions: () -> Unit) = apply {
        setUps.add(Condition(setUpsCounter.getAndIncrement(), key, actions))
    }

    open fun addTearDown(key: String = ALL_SETUP_KEY, actions: () -> Unit) = apply {
        tearDowns.add(Condition(tearDownsCounter.getAndIncrement(), key, actions))
    }

    override fun starting(description: Description) {
        val keys = mutableListOf(ALL_SETUP_KEY)
        val method = description.testClass.getMethod(description.methodName)
        if (method.isAnnotationPresent(SetUp::class.java)) {
            keys.addAll(method.getAnnotation(SetUp::class.java).value.toList()) //get the list of keys in annotation SetUp
            setUps
                .sortedBy { it.counter }
                .filter { it.key in keys }
                .forEach { condition ->
                    condition.actions()
                }
        } else {
            setUps.filter { it.key == ALL_SETUP_KEY }.forEach { condition ->
                condition.actions()
            }
        }
        super.starting(description)
    }

    override fun finished(description: Description) {
        val keys = mutableListOf(ALL_SETUP_KEY)
        val method = description.testClass.getMethod(description.methodName)
        if (method.isAnnotationPresent(TearDown::class.java)) {
            keys.addAll(method.getAnnotation(TearDown::class.java).value.toList()) //get the list of keys in annotation TearDown
            tearDowns
                .sortedBy { it.counter }
                .filter { it.key in keys }
                .forEach { condition ->
                    condition.actions()
                }
        } else {
            tearDowns.filter { it.key == ALL_SETUP_KEY }.forEach { condition ->
                condition.actions()
            }
        }
        super.finished(description)
    }

    private inner class Condition(val counter: Int, val key: String, val actions: () -> Unit)
}