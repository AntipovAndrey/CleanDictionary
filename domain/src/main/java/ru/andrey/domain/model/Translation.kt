package ru.andrey.domain.model

data class Translation(
        var id: Int?,
        var word: String,
        var translation: String,
        var languageFrom: Language,
        var languageTo: Language,
        var favorite: Boolean
) {
    constructor(word: String, translation: String, languageFrom: Language, languageTo: Language)
            : this(null, word, translation, languageFrom, languageTo, false)
}