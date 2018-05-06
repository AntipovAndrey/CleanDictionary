package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;

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
        return mTranslationService.getTranslation(translation);
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
