package ru.andrey.cleandictionary.domain.global;

import io.reactivex.Single;
import ru.andrey.cleandictionary.domain.model.Translation;

public interface TranslationService {

    Single<Translation> getTranslation(Translation word);
}
