package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.di.translation.TranslationScope;
import ru.andrey.cleandictionary.model.Translation;

@TranslationScope
public class FavoriteTranslationInteractor {

    private TranslationInteractor mTranslationInteractor;

    @Inject
    public FavoriteTranslationInteractor(TranslationInteractor translationInteractor) {
        mTranslationInteractor = translationInteractor;
    }

    public Single<Translation> toggleFavorite(Translation item) {
        return Single.just(item)
                .map(Translation::new)
                .doOnSuccess(Translation::toggleFavorite)
                .flatMap(mTranslationInteractor::saveWord)
                .subscribeOn(Schedulers.io());
    }
}
