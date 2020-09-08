package com.soufianekre.redpass.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soufianekre.redpass.data.db.convertors.LabelDbConverter
import com.soufianekre.redpass.data.db.doa.LabelDoa
import com.soufianekre.redpass.data.db.doa.PasswordItemDoa
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem

@Database(entities = [PasswordItem::class, Label::class],version =5,exportSchema = false)
@TypeConverters(LabelDbConverter::class)
abstract class AppDatabase :RoomDatabase(){

    companion object {
        const val DB_NAME : String = "passwords.db"
        private var instance:AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return  instance!!
        }


    }

    abstract fun passwordItemDoa() : PasswordItemDoa
    abstract fun labelDoa():LabelDoa

}