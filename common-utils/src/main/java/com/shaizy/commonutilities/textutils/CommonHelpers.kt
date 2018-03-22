package com.shaizy.commonutilities.textutils

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

/**
 * Created by syedshahnawazali on 21/03/2018.
 * Common Text related utils for commerce apps
 */

fun <T> T.price(locale: Locale = Locale.US): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    return when (this) {
        is String, is CharSequence -> {
            try {
                String.format("%s", formatter.format(this.toString().toFloat()))
            } catch (e: NumberFormatException) {
                this.toString()
            }
        }
        is Double, is Float, is Int, is Long -> formatter.format(this)
        else -> "-"
    }
}

fun <T : EditText> T.setDoneAction(action: (text: String) -> Unit) {
    setOnEditorActionListener { v, actionId, event ->
        if (v.text.isNotBlank() && (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER))) {
            action.invoke(v.text.toString())
            return@setOnEditorActionListener false
        }
        true
    }
}