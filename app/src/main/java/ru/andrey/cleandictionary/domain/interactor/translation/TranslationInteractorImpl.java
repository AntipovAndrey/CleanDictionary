package ru.andrey.cleandictionary.domain.interactor.translation;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.domain.global.TranslationRepository;
import ru.andrey.cleandictionary.domain.global.TranslationService;
import ru.andrey.cleandictionary.domain.model.Translation;

public class TranslationInteractorImpl implements TranslationInteractor {

    private TranslationService mTranslationService;
    private TranslationRepository mRepository;

    public TranslationInteractorImpl(TranslationService translationService, TranslationRepository repository) {
        mTranslationService = translationService;
        mRepository = repository;
    }

    @Override
    public Single<Translation> getTranslation(Translation translation) {
        checkModel(translation);
        return mTranslationService.getTranslation(translation)
                .subscribeOn(Schedulers.io());
    }

    private void checkModel(Translation translation) {
        if (translation == null ||
                translation.getLanguageFrom() == null ||
                translation.getLanguageTo() == null) {
            throw new IllegalArgumentException("Invalid translation " +
                    (translation != null ? translation.toString() : null));
        }
    }

    @Override
    public Single<Translation> saveWord(Translation t) {
        return mRepository.save(t)
                .subscribeOn(Schedulers.io());
    }
}
