package com.soufianekre.redpass.data.db.doa

import androidx.room.*
import com.soufianekre.redpass.data.db.models.PasswordItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class PasswordItemDoa{

    @Insert
    abstract fun insertPasswordItem(passwordList :PasswordItem) : Completable
    @Query("SELECT * from password_item")
    abstract fun getPasswordList() : Flowable<List<PasswordItem>>

    @Query("SELECT * from password_item where id= :password_id LIMIT 1")
    abstract fun getPasswordItem(password_id: Int) : Single<PasswordItem>

    @Delete
    abstract fun deletePasswordItem(passwordItem: PasswordItem) :Completable

    @Update
    abstract fun updatePasswordItem(passwordItem : PasswordItem) :Completable
}