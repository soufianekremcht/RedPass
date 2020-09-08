package com.soufianekre.redpass.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "label")
data class Label(
    @ColumnInfo
    var name:String) : Serializable{

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0


}