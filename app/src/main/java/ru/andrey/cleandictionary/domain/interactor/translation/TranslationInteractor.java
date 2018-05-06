package ru.andrey.cleandictionary.domain.interactor.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.domain.model.Translation;

public interface TranslationInteractor {
    /**
     * Returns Single with model which is carried translation
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @param translation model to be translated
     * @return Single of model wuth translation
     */
    Single<Translation> getTranslation(Translation translation);

    /**
     * Returns Single with saved word
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @param translation model to be saved
     * @return Single of saved model
     */
    Single<Translation> saveWord(Translation translation);
}
