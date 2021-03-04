package com.soufianekre.redpass.data

import com.soufianekre.redpass.data.db.RoomDb

interface DataManager{

    fun getAppDatabase() : RoomDb
}