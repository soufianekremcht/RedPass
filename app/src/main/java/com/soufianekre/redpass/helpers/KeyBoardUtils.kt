package com.soufianekre.redpass.helpers

import android.content.Context.INPUT_METHOD_SERVICE
import android.widget.EditText
import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager


object KeyboardUtils {

    fun hideSoftInput(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        val imm = activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSoftInput(context: Context, edit: EditText) {
        if (edit.requestFocus()) {
            edit.isFocusable = true
            edit.isFocusableInTouchMode = true
            edit.requestFocus()
            val imm = context
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit, 0)
        }

    }

    fun switchToNumeric(context: Context, edit: EditText) {
        if (edit.requestFocus()) {
            edit.isFocusable = true
            edit.isFocusableInTouchMode = true
            if (edit.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD)
                edit.inputType = InputType.TYPE_CLASS_NUMBER
            else
                edit.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            edit.requestFocus()
            val imm = context
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit, 0)
        }

    }

    fun toggleSoftInput(context: Context) {
        val imm = context
            .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

}// This utility class is not publicly instantiable
