package com.atiurin.espressopageobjectexample.activity

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.webkit.WebView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.atiurin.espressopageobjectexample.R

class UiElementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uielements)
        val simpleButton: Button = findViewById(R.id.button1)
        val enableCheckBox: CheckBox = findViewById(R.id.checkbox_enable)
        val clickableCheckBox: CheckBox = findViewById(R.id.checkbox_clickable)
        val selectedCheckBox: CheckBox = findViewById(R.id.checkbox_selected)
        val focusableCheckBox: CheckBox = findViewById(R.id.checkbox_focusable)
        val radioGroupVisibility: RadioGroup = findViewById(R.id.radio_group_visibility)
        val etContentDescription: EditText = findViewById(R.id.et_contentDesc)
        val webView: WebView = findViewById(R.id.webview)
        val jsCheckBox: CheckBox = findViewById(R.id.checkbox_js_enabled)

        webView.settings.javaScriptEnabled = true
        val customHtml = "<html><body>Hello, WebView</body></html>"
        webView.loadData(customHtml, "text/html", "UTF-8")
        enableCheckBox.setOnClickListener { view ->
            simpleButton.isEnabled = (view as CheckBox).isChecked
        }
        clickableCheckBox.setOnClickListener { view ->
            simpleButton.isClickable = (view as CheckBox).isChecked
        }
        selectedCheckBox.setOnClickListener { view ->
            simpleButton.isSelected = (view as CheckBox).isChecked
        }
        jsCheckBox.setOnClickListener { view ->
            webView.settings.javaScriptEnabled = (view as CheckBox).isChecked
        }
        radioGroupVisibility.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_visible -> simpleButton.visibility = View.VISIBLE
                R.id.radio_invisible -> simpleButton.visibility = View.INVISIBLE
                R.id.radio_gone -> simpleButton.visibility = View.GONE
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusableCheckBox.visibility = VISIBLE
            focusableCheckBox.setOnClickListener { view ->
                if ((view as CheckBox).isChecked) {
                    simpleButton.focusable = FOCUSABLE
                } else simpleButton.focusable = NOT_FOCUSABLE
            }
        }
        val addTextChangedListener =
            etContentDescription.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(text: Editable?) {
                    simpleButton.contentDescription = text
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
    }
}