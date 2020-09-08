package com.soufianekre.redpass.data.db.convertors

import android.text.TextUtils
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.soufianekre.redpass.data.db.models.Label


class LabelDbConverter{
    @TypeConverter
    fun stringToLabel(string: String): Label? {
        return if (!TextUtils.isEmpty(string)) {
            val type = object : TypeToken<Label>() {

            }.type
            Gson().fromJson(string, type)
        } else {
            null
        }
    }

    @TypeConverter
    fun fromLabel(label: Label): String {
        val gson = Gson()
        return gson.toJson(label)
    }
}