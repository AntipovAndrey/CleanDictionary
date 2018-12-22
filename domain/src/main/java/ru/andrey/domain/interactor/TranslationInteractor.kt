package ru.andrey.domain.interactor

import io.reactivex.Scheduler
import io.reactivex.Single
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationService

class TranslationInteractor(private val ioScheduler: Scheduler,
                            private val translationService: TranslationService) {

    /**
     * Returns Single with model which is carried translation
     *
     *
     * Subscribed on provided scheduler
     *
     * @param word word to be translated,
     * @return Single of model with translation
     */
    fun getTranslation(word: String, from: Language, to: Language): Single<Translation> {
        return translationService
                .getTranslation(word = word, from = from.languageCode, to = to.languageCode)
                .map { Translation(word, it, from, to) }
                .subscribeOn(ioScheduler)
    }
}
