package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.model.Translation;

public class FavoriteTranslationInteractorImpl implements FavoriteTranslationInteractor {

    private TranslationInteractor mTranslationInteractor;

    public FavoriteTranslationInteractorImpl(TranslationInteractor translationInteractor) {
        mTranslationInteractor = translationInteractor;
    }

    @Override
    public Single<Translation> toggleFavorite(Translation item) {
        return Single.just(item)
                .map(Translation::new)
                .doOnSuccess(Translation::toggleFavorite)
                .flatMap(mTranslationInteractor::saveWord)
                .subscribeOn(Schedulers.io());
    }
}
