package com.soufianekre.redpass.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.soufianekre.redpass.data.db.convertors.LabelDbConverter
import com.soufianekre.redpass.data.db.doa.LabelDoa
import com.soufianekre.redpass.data.db.doa.PasswordItemDoa
import com.soufianekre.redpass.data.db.models.Label
import com.soufianekre.redpass.data.db.models.PasswordItem

@Database(entities = [PasswordItem::class, Label::class],version =6 ,exportSchema = false)
@TypeConverters(LabelDbConverter::class)
abstract class RoomDb :RoomDatabase(){

    companion object {
        const val DB_NAME : String = "passwords.db"
        private var instance:RoomDb? = null

        fun getInstance(context: Context) : RoomDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context, RoomDb::class.java, DB_NAME)

                    .addMigrations(MIGRATION_5_6)
                    .build()
            }
            return  instance!!
        }

        private val MIGRATION_5_6 = object : Migration(5, 6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE label ADD COLUMN color TEXT")
            }
        }


    }



    abstract fun passwordItemDoa() : PasswordItemDoa
    abstract fun labelDoa():LabelDoa



}