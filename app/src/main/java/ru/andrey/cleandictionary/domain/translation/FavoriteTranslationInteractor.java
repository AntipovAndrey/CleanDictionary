package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Single;
import ru.andrey.cleandictionary.model.Translation;

public interface FavoriteTranslationInteractor {
    /**
     * Return Single with saved new model
     * <p>
     * Subscribed on Schedulers.io()
     *
     * @param item model to toggle favorite
     * @return Single with new model toggled to favorite
     */
    Single<Translation> toggleFavorite(Translation item);
}
