package com.soufianekre.redpass.helpers

import java.text.SimpleDateFormat

object DateHelper {

    fun format(date: Long, format: String):String{
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date).toString()

    }
}