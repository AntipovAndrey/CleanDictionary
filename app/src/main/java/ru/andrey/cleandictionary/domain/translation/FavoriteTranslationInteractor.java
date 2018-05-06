package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.model.Translation;

public interface FavoriteTranslationInteractor {
    Single<Translation> toggleFavorite(Translation item);
}
