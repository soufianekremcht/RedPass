package com.soufianekre.redpass.data

import android.content.Context
import android.content.SharedPreferences
/*import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys*/
import com.soufianekre.redpass.MyApp
import com.soufianekre.redpass.data.app_pref.AppPreferenceHelper
import com.soufianekre.redpass.data.db.AppDatabase


class AppDataManager() :DataManager{



    private val appContext: Context = MyApp.getContext()

    init {
        /*   val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            appContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )*/
    }


    override fun getAppDatabase(): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

}