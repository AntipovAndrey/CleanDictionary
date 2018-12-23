package ru.andrey.data.db

import android.arch.persistence.room.*
import ru.andrey.data.db.entity.TranslationData

@Dao
interface TranslationDao {

    @Query("SELECT * FROM translations ORDER BY id DESC")
    fun getAll(): List<TranslationData>

    @Query("SELECT * FROM translations WHERE id=:id")
    fun findById(id: Int): TranslationData?

    @Insert
    fun save(item: TranslationData): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: TranslationData)

    @Query("DELETE FROM translations WHERE id=:id")
    fun delete(id: Int)
}
