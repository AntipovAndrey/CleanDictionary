package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.model.Translation;

public interface TranslationInteractor {
    Single<Translation> getTranslation(Translation translation);

    Single<Translation> saveWord(Translation t);
}
