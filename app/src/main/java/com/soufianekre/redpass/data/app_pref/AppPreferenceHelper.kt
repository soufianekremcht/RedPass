package com.soufianekre.redpass.data.app_pref

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.soufianekre.redpass.helpers.InputHelper



class AppPreferenceHelper(context: Context) : PreferenceHelper{


    private var mPrefs: SharedPreferences? = null


    init {
        mPrefs = context.getSharedPreferences(PrefConst.PREF_NAME, Context.MODE_PRIVATE)
    }

    override operator fun <T> set(key: String, value: T) {
        if (InputHelper.isEmpty(key)) {
            throw NullPointerException("Key must not be null! (key = $key), (value = $value)")
        }
        val edit = mPrefs!!.edit()
        if (InputHelper.isEmpty(value)) {
            clearKey(key)
            return
        }
        when (value) {
            is String -> edit.putString(key, value as String?)
            is Int -> edit.putInt(key, (value as Int?)!!)
            is Long -> edit.putLong(key, (value as Long?)!!)
            is Boolean -> edit.putBoolean(key, (value as Boolean?)!!)
            is Float -> edit.putFloat(key, (value as Float?)!!)
            else -> edit.putString(key, value!!.toString())
        }
        edit.apply()//apply on UI
    }


    @Nullable
    override fun getString(@NonNull key: String, defaults: String): String? {
        return mPrefs!!.getString(key, defaults)
    }

    override fun getBoolean(@NonNull key: String,defaults :Boolean): Boolean {
        return mPrefs!!.getBoolean(key, defaults)
    }

    override fun getInt(@NonNull key: String): Int {
        return if (mPrefs?.all!![key] is Int) mPrefs!!.getInt(key, 0) else -1
    }

    override fun getLong(@NonNull key: String): Long {
        return mPrefs!!.getLong(key, 0)
    }

    override fun getFloat(@NonNull key: String): Float {
        return mPrefs!!.getFloat(key, 0f)
    }

    override fun clearKey(@NonNull key: String) {
        mPrefs!!.edit().remove(key).apply()
    }

    override fun isExist(@NonNull key: String): Boolean {
        return mPrefs!!.contains(key)
    }

    fun clearPrefs() {
        mPrefs!!.edit().clear().apply()
    }

    override fun getAll(): Map<String, *> {
        return mPrefs!!.getAll()
    }


}