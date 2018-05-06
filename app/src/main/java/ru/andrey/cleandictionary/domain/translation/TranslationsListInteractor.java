package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Observable;
import ru.andrey.cleandictionary.model.Translation;

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
