package com.soufianekre.redpass.data.app_pref

import androidx.annotation.NonNull
import androidx.annotation.Nullable

interface PreferenceHelper{
    operator fun <T> set(@NonNull key: String, @Nullable value: T) :Unit


    fun getString(@NonNull key: String, defaults: String): String?

    fun getInt(@NonNull key: String): Int
    fun getLong(@NonNull key: String): Long
    fun getBoolean(@NonNull key: String,defaults: Boolean): Boolean
    fun getFloat(@NonNull key: String): Float
    fun isExist(@NonNull key: String): Boolean
    fun clearKey(@NonNull key: String)
    fun getAll(): Map<String, *>

}