package com.soufianekre.redpass.helpers

import android.content.Context
import android.graphics.Color


object  AppHelper {


    fun getRandomMaterialColor(context: Context, typeColor: Int): Int {
        var returnColor = Color.GRAY
        val arrayId = context.resources.getIdentifier("mdcolor_$typeColor", "array", context.getPackageName())

        if (arrayId != 0) {
            val colors = context.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }
}