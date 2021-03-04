package com.soufianekre.redpass.data

import android.content.Context
/*import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys*/
import com.soufianekre.redpass.RedPassApp
import com.soufianekre.redpass.data.db.RoomDb


class AppDataManager() :DataManager{



    private val appContext: Context = RedPassApp.getContext()

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


    override fun getAppDatabase(): RoomDb {
        return RoomDb.getInstance(appContext)
    }

}