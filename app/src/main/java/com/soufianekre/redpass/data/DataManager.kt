package com.soufianekre.redpass.data

import com.soufianekre.redpass.data.app_pref.AppPreferenceHelper
import com.soufianekre.redpass.data.db.AppDatabase

interface DataManager{

    fun getAppDatabase() : AppDatabase
    fun getAppPreference(): AppPreferenceHelper
}