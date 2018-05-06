package ru.andrey.cleandictionary.domain.translation;

import io.reactivex.Observable;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;

public interface TranslationsListInteractor {
    Observable<DictionaryItemPresenter> getTranslations();
}
