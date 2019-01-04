package ru.andrey.cleandictionary.di.translation.yandex

import retrofit2.Retrofit
import ru.andrey.cleandictionary.di.translation.TranslationModule
import ru.andrey.data.api.YandexApi
import ru.andrey.data.repository.YandexTranslationService
import ru.andrey.domain.repository.TranslationService

class YandexTranslationModule : TranslationModule() {

    override fun provideTranslationService(retrofit: Retrofit): TranslationService {
        return YandexTranslationService(retrofit.create(YandexApi::class.java))
    }
}