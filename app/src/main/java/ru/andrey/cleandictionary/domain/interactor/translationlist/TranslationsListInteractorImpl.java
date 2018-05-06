package ru.andrey.cleandictionary.domain.interactor.translationlist;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.domain.global.TranslationRepository;
import ru.andrey.cleandictionary.domain.model.Translation;

public class TranslationsListInteractorImpl implements TranslationsListInteractor {

    private TranslationRepository mRepository;

    @Inject
    public TranslationsListInteractorImpl(TranslationRepository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<Translation> getTranslations() {
        return mRepository.getAll()
                .subscribeOn(Schedulers.io());
    }
}
