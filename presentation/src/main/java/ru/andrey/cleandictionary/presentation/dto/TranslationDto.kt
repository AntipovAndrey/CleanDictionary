package ru.andrey.cleandictionary.presentation.dto

data class TranslationDto(val id: Int,
                          val word: WordDto,
                          val translation: String,
                          val favorite: Boolean)
