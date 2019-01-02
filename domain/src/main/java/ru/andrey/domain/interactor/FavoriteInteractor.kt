package ru.andrey.domain.interactor

import io.reactivex.Completable
import io.reactivex.Scheduler
import ru.andrey.domain.repository.TranslationRepository

class FavoriteInteractor(private val ioScheduler: Scheduler,
                         private val translationRepository: TranslationRepository) {

    /**
     * Return Single with saved new model
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @param itemId id of model to toggle favorite
     * @return Completable
     */
    fun toggleFavorite(itemId: Int): Completable {
        return translationRepository.findById(itemId)
                .doOnSuccess { it.favorite = !it.favorite }
                .flatMapCompletable { translationRepository.update(it) }
                .subscribeOn(ioScheduler)
    }
}