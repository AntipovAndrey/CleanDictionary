package ru.andrey.cleandictionary.presentation.dto

import ru.andrey.domain.model.Translation

data class TranslationDto(val id: Int,
                          val word: WordDto,
                          val translation: String,
                          var favorite: Boolean) {
    companion object {
        fun fromModel(it: Translation): TranslationDto {
            return TranslationDto(
                    id = it.id!!,
                    translation = it.translation,
                    favorite = it.favorite,
                    word = WordDto(it.word,
                            it.languageFrom.languageCode,
                            it.languageTo.languageCode)
            )
        }
    }
}
