package com.soufianekre.redpass

import android.app.Application
import android.content.Context


class MyApp: Application(){


    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    companion object{
        private var instance: MyApp? = null


        fun getContext() : Context {
            return instance!!.applicationContext
        }
    }


}