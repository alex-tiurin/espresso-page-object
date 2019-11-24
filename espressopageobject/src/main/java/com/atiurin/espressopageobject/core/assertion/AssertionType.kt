package com.atiurin.espressopageobject.core.assertion

import com.atiurin.espressopageobject.core.OperationType

enum class AssertionType : OperationType {
    IS_DISPLAYED, IS_COMPLETELY_DISPLAYED, IS_DISPLAYING_AT_LEAST,
    IS_ENABLED, IS_SELECTED, IS_CLICKABLE, IS_CHECKED, IS_NOT_CHECKED,
    IS_FOCUSABLE, IS_JS_ENABLED, HAS_TEXT, ASSERT_MATCHES
}