package ru.andrey.cleandictionary.presentation.presenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.domain.interactor.favorite.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.model.Translation;
import ru.andrey.cleandictionary.presentation.view.WordView;

public class DictionaryItemPresenter {

    private Translation mTranslation;
    private WordView mWordView;

    @Inject
    FavoriteTranslationInteractor mInteractor;


    public DictionaryItemPresenter(Translation translation) {
        App.instance.getTranslationComponent().inject(this);
        mTranslation = translation;
    }

    public String getHeader() {
        return mTranslation.getWord();
    }

    public String getTranslation() {
        return mTranslation.getTranslation();
    }

    public void starClicked() {
        mInteractor.toggleFavorite(mTranslation)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(translation -> {
                    mTranslation = translation;
                    mWordView.setStar(mTranslation.isFavorite());
                })
                .subscribe();
    }

    public boolean isFavorite() {
        return mTranslation.isFavorite();
    }

    public String getLangFrom() {
        return mTranslation.getLanguageFrom().getLanguageCode();
    }

    public String getLangTo() {
        return mTranslation.getLanguageTo().getLanguageCode();
    }

    public void setView(WordView wordView) {
        mWordView = wordView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryItemPresenter item = (DictionaryItemPresenter) o;

        return mTranslation != null ? mTranslation.equals(item.mTranslation) : item.mTranslation == null;
    }

    @Override
    public int hashCode() {
        return mTranslation != null ? mTranslation.hashCode() : 0;
    }

    @Override
    public String toString() {
        return mTranslation.toString();
    }
}
