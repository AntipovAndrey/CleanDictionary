package ru.andrey.data.repository

import io.reactivex.Single
import ru.andrey.data.api.YandexApi
import ru.andrey.domain.repository.TranslationService

class YandexTranslationService(private val api: YandexApi) : TranslationService {

    override fun getTranslation(word: String, from: String, to: String): Single<String> {
        return api.getTranslation(word, "$from-$to")
                .map { it.translations[0] }
    }
}
