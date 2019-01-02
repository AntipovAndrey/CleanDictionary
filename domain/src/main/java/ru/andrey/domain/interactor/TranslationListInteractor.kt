package ru.andrey.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.andrey.domain.model.Translation
import ru.andrey.domain.repository.TranslationRepository

class TranslationListInteractor(private val ioScheduler: Scheduler,
                                private val translationRepository: TranslationRepository) {

    /**
     * Returns Single with saved word
     *
     *
     * Subscribed on provided scheduler
     *
     * @param translation model to be saved
     * @return Completable of saved model
     */
    fun saveWord(translation: Translation): Completable {
        return when (translation.id) {
            null -> translationRepository.save(translation)
            else -> translationRepository.update(translation)
        }.subscribeOn(ioScheduler)
    }

    /**
     * Fetches models from repository
     *
     *
     * Subscribed on provided scheduler
     *
     * @return Observable of fetched translation
     */
    fun getTranslations(): Observable<List<Translation>> {
        return translationRepository.getAll()
                .subscribeOn(ioScheduler)
    }

    /**
     * Deletes item matches id
     *
     * @param id of item
     */
    fun deleteWord(id: Int): Completable {
        return translationRepository.delete(id)
                .subscribeOn(ioScheduler)
    }

    /**
     * Restores previously deleted item matches id
     *
     * @param id of item
     */
    fun restoreWord(id: Int): Completable {
        return translationRepository.restore(id)
                .subscribeOn(ioScheduler)
    }
}