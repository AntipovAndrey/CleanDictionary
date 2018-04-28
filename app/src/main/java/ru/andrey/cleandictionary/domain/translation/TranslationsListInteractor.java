package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;


public class TranslationsListInteractor {

    @Inject
    TranslationRepository mRepository;

    public TranslationsListInteractor() {
        App.instance.getAppComponent().inject(this);
    }

    public Observable<DictionaryItemPresenter> getTranslationsObserver() {
        return mRepository.getAll()
                .map(DictionaryItemPresenter::new)
                .subscribeOn(Schedulers.io());
    }
}
