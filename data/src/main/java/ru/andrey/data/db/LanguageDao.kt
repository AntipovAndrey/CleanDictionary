package ru.andrey.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.andrey.data.db.entity.LanguageData

@Dao
interface LanguageDao {

    @Query("SELECT * FROM languages WHERE code=:code")
    fun findByCode(code: String): LanguageData?

    @Query("SELECT * FROM languages WHERE id=:id")
    fun findById(id: Int): LanguageData?
}
