package ru.andrey.data.repository

import io.reactivex.Single
import ru.andrey.data.api.MicrosoftApi
import ru.andrey.data.api.MicrosoftApiRequest
import ru.andrey.domain.repository.TranslationService

class MicrosoftTranslationService(private val api: MicrosoftApi) : TranslationService {

    override fun getTranslation(word: String, from: String, to: String): Single<List<String>> {
        return api.getTranslation(MicrosoftApiRequest(word), from, to)
                .map { it.translations }
                .map { it.map { translation -> translation.displayTarget } }
                .filter { it.isNotEmpty() }
                .switchIfEmpty(api.getLongTranslation(listOf(MicrosoftApiRequest(word)), from, to)
                        .map { responseArray -> responseArray[0] }
                        .map { it.translations }
                        .map { it.map { translation -> translation.text } })
    }
}
