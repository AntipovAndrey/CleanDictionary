package ru.andrey.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.andrey.data.db.entity.LanguageData
import ru.andrey.data.db.entity.TranslationData

@Database(entities = [TranslationData::class, LanguageData::class],
        version = 1,
        exportSchema = false)
abstract class TranslationDatabase : RoomDatabase() {

    abstract fun translationDao(): TranslationDao

    abstract fun languageDao(): LanguageDao
}
