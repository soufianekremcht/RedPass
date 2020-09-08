package com.soufianekre.redpass.data.db.doa

import androidx.room.*
import com.soufianekre.redpass.data.db.models.Label
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
abstract class LabelDoa{

    @Query("SELECT * from label")
    abstract fun getLabels(): Flowable<List<Label>>

    @Insert
    abstract fun insertLabel(label : Label) : Completable

    @Delete
    abstract fun deleteLabel(label :Label) : Completable

    @Update
    abstract fun updateLabel(labe: Label) : Completable





}