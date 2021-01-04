package com.soufianekre.redpass

import android.app.Application
import android.content.Context
import android.util.Log
import com.github.ajalt.reprint.core.Reprint
import com.soufianekre.redpass.data.app_pref.AppPreferenceHelper
import timber.log.Timber


class RedPassApp: Application(){


    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        instance = this
        prefs = AppPreferenceHelper(this)
        Reprint.initialize(this, object : Reprint.Logger {
            override fun log(message: String) {
                Log.d("Reprint", message)
            }

            override fun logException(
                throwable: Throwable,
                message: String
            ) {
                Log.e("Reprint", message, throwable)
            }
        })
    }

    companion object{
        private var instance: RedPassApp? = null
        private var  prefs: AppPreferenceHelper? = null

        fun getContext() : Context {
            return instance!!.applicationContext
        }
        fun getPref() : AppPreferenceHelper{
            return prefs!!
        }
    }


}