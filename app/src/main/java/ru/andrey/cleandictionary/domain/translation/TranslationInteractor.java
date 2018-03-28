package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.network.translation.YandexTranslate;
import ru.andrey.cleandictionary.data.repository.InMemoryRepository;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.model.Translation;

public class TranslationInteractor {

    @Inject
    TranslationService mTranslationService = new YandexTranslate();
    TranslationRepository mRepository = InMemoryRepository.getInstance();

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
            throw new IllegalArgumentException("Invalid translation");
        }
    }

    public void saveWord(Translation t) {
        mRepository.save(t);
    }
}
