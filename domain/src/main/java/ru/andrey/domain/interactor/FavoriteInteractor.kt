package ru.andrey.domain.interactor

import io.reactivex.Scheduler
import io.reactivex.Single
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository

class FavoriteInteractor(private val ioScheduler: Scheduler,
                         private val translationRepository: TranslationRepository) {

    /**
     * Return Single with saved new model
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @param itemId id of model to toggle favorite
     * @return Single with new model toggled to favorite
     */
    fun toggleFavorite(itemId: Int): Single<Translation> {
        return translationRepository.findById(itemId)
                .doOnSuccess { it.favorite = !it.favorite }
                .flatMap { translationRepository.update(it) }
                .subscribeOn(ioScheduler)
    }
}