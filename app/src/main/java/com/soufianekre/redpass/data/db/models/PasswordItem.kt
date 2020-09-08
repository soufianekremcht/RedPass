package com.soufianekre.redpass.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable
import java.util.*

@Entity(tableName = "password_item")
data class PasswordItem(
    @ColumnInfo(name = "title")
    var title :String = "",
    @ColumnInfo(name = "username")
    var username: String ="",
    @ColumnInfo(name = "password")
    var password: String="",
    @ColumnInfo(name="account_use")
    var account_use:String="",
    @ColumnInfo(name = "notes")
    var notes:String="",
    @ColumnInfo(name = "last_updated_date")
    var lastUpdatedDate : Long= Date().time,
    @Expose
    @ColumnInfo(name="label")
    var label : Label = Label("No Label")):Serializable
{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}