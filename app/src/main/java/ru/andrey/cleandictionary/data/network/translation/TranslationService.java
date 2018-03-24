package ru.andrey.cleandictionary.data.network.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.model.Translation;


public interface TranslationService {

    Single<Translation> getTranslation(Translation word);
}
