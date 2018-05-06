package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;

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
