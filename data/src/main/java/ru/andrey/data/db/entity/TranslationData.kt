package ru.andrey.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationData(

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var word: String,
        var translation: String,
        var from: Int,
        var to: Int,
        var deleted: Boolean,
        var favorite: Boolean
)
