package ru.andrey.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "languages", indices = [Index(value = ["code"], unique = true)])
data class LanguageData(

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var code: String
)
