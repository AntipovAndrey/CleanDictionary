package ru.andrey.cleandictionary.presentation.dto

data class TranslationDto(val word: WordDto,
                          val translation: String,
                          val favorite: Boolean)

