package ru.andrey.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageData(

        @PrimaryKey
        var id: Int,
        var code: String
)
