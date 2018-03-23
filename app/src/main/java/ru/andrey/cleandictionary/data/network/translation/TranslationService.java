package ru.andrey.cleandictionary.data.network.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.model.Translation;


public interface TranslationService {

    void setWordToTranslate(Translation word);

    Single<String> getTranslation();
}
