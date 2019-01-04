package ru.andrey.data.repository

import io.reactivex.Single
import ru.andrey.data.api.MicrosoftApi
import ru.andrey.data.api.MicrosoftApiRequest
import ru.andrey.domain.repository.TranslationService

class MicrosoftTranslationService(private val api: MicrosoftApi) : TranslationService {

    override fun getTranslation(word: String, from: String, to: String): Single<List<String>> {
        return api.getTranslation(MicrosoftApiRequest(word), from, to)
                .map { it.translations.map { translation -> translation.displayTarget } }
    }
}