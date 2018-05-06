package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.di.translation.TranslationScope;
import ru.andrey.cleandictionary.model.Translation;

@TranslationScope
public class TranslationInteractor {

    private TranslationService mTranslationService;
    private TranslationRepository mRepository;

    @Inject
    public TranslationInteractor(TranslationService translationService, TranslationRepository repository) {
        mTranslationService = translationService;
        mRepository = repository;
    }

    public SingleObserver<Translation> getTranslation(SingleObserver<Translation> translationObserver,
                                                      Scheduler observeOnScheduler, Translation translation) {
        checkModel(translation);
        return mTranslationService.getTranslation(translation)
                .observeOn(observeOnScheduler)
                .subscribeWith(translationObserver);
    }

    private void checkModel(Translation translation) {
        if (translation == null ||
                translation.getLanguageFrom() == null ||
                translation.getLanguageTo() == null) {
            throw new IllegalArgumentException("Invalid translation " +
                    (translation != null ? translation.toString() : null));
        }
    }

    public Single<Translation> saveWord(Translation t) {
        return mRepository.save(t);
    }
}
