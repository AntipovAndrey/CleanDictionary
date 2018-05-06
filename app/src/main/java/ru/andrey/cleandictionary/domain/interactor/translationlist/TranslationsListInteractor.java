package ru.andrey.cleandictionary.domain.interactor.translationlist;

import io.reactivex.Observable;
import ru.andrey.cleandictionary.domain.model.Translation;

public interface TranslationsListInteractor {
    /**
     * Fetches models from repository
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @return Observable of fetched translation
     */
    Observable<Translation> getTranslations();
}
