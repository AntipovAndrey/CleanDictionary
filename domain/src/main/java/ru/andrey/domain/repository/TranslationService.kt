package ru.andrey.domain.repository

import io.reactivex.Single

interface TranslationService {

    fun getTranslation(word: String, from: String, to: String): Single<String>
}
