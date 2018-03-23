package ru.andrey.cleandictionary.domain.translation;

import javax.inject.Inject;

import io.reactivex.Observer;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.network.translation.YandexTranslate;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;

public class TranslationInteractor {

    @Inject
    TranslationService mTranslationService = new YandexTranslate();

    private final Translation mTranslation;

    public TranslationInteractor(String word, Language from, Language to) {
        this(new Translation(word, from, to));
    }

    public TranslationInteractor(Translation translation) {
        if (translation == null ||
                translation.getLanguageFrom() == null ||
                translation.getLanguageTo() == null) {
            throw new IllegalArgumentException("Invalid translation");
        }
        mTranslation = translation;
    }

    public void getTranslation(Observer<Translation> translationObserver) {
        mTranslationService.setWordToTranslate(mTranslation);
        mTranslationService.getTranslation()
                .subscribe(s -> {
                    mTranslation.setTranslation(s);
                    System.out.println("call");
                    translationObserver.onNext(mTranslation);
                });
    }

}
