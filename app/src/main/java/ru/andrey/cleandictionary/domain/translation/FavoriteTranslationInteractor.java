package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;

public class FavoriteTranslationInteractor {
    @Inject
    TranslationRepository mRepository;

    public FavoriteTranslationInteractor() {
        App.instance.getAppComponent().inject(this);
    }

    public Completable toggleFavorite(DictionaryItemPresenter item) {
        return Completable.fromRunnable(() -> {
            final Translation model = item.getTranslationModel();
            model.toggleFavorite();
            mRepository.save(model)
                    .subscribe();
        }).subscribeOn(Schedulers.io());
    }
}
