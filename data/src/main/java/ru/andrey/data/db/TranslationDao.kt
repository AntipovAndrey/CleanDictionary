package ru.andrey.data.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.andrey.data.db.entity.TranslationData

@Dao
interface TranslationDao {

    @Query("SELECT * FROM translations WHERE deleted = 0 ORDER BY id DESC")
    fun getAll(): Flowable<List<TranslationData>>

    @Query("SELECT * FROM translations WHERE id = :id AND deleted = 0")
    fun findById(id: Int): Single<TranslationData>

    @Insert
    fun save(item: TranslationData): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: TranslationData)

    @Query("UPDATE translations SET deleted = 1 WHERE id = :id")
    fun delete(id: Int)

    @Query("UPDATE translations SET deleted = 0 WHERE id = :id")
    fun restore(id: Int)
}
