package ru.andrey.cleandictionary.di.translation.microsoft

import retrofit2.Retrofit
import ru.andrey.cleandictionary.di.translation.TranslationModule
import ru.andrey.data.api.MicrosoftApi
import ru.andrey.data.repository.MicrosoftTranslationService
import ru.andrey.domain.repository.TranslationService

class MicrosoftTranslationModule : TranslationModule() {

    override fun provideTranslationService(retrofit: Retrofit): TranslationService {
        return MicrosoftTranslationService(retrofit.create(MicrosoftApi::class.java))
    }
}