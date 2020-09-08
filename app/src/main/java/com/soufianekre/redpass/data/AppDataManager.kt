package com.soufianekre.redpass.data

import android.content.Context
import com.soufianekre.redpass.MyApp
import com.soufianekre.redpass.data.app_pref.AppPreferenceHelper
import com.soufianekre.redpass.data.db.AppDatabase




class AppDataManager() :DataManager{


    private val appContext: Context

    init {
        this.appContext = MyApp.getContext()
    }


    override fun getAppDatabase(): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    override fun getAppPreference(): AppPreferenceHelper {
        return AppPreferenceHelper(appContext)
    }

}