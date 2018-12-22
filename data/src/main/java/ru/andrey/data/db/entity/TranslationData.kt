package ru.andrey.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "translations")
data class TranslationData(

        @PrimaryKey(autoGenerate = true)
        var id: Int,
        var word: String,
        var translation: String,
        var from: Int,
        var to: Int,
        var favorite: Boolean
)
