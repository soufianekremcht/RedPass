package com.soufianekre.redpass.helpers

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.view.Window
import android.view.WindowManager


object ColorsHelper {


    fun changeStatusBarColor(activity: Activity, color: Int) {
        if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            if (color != 0) window.statusBarColor = color
        }
    }

    fun getColor(context: Context, i: Int): Int {
        return if (SDK_INT >= 23)
            context.resources.getColor(i,null)
        else
            context.resources.getColor(i)

    }
}