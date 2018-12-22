package ru.andrey.cleandictionary.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.andrey.cleandictionary.presentation.view.AddWordActivity;
import ru.andrey.cleandictionary.presentation.view.WordListView;
import ru.andrey.domain.interactor.FavoriteInteractor;
import ru.andrey.domain.interactor.TranslationsListInteractor;
import ru.andrey.domain.model.Translation;

@InjectViewState
public class DictionaryListPresenter extends MvpPresenter<WordListView> {

    private final TranslationsListInteractor mListInteractor;
    private final FavoriteInteractor mFavoriteTranslationInteractorInteractor;

    private boolean mFavoriteEnabled;

    private CompositeDisposable mDisposables;

    @Inject
    public DictionaryListPresenter(TranslationsListInteractor mListInteractor,
                                   FavoriteInteractor mFavoriteTranslationInteractorInteractor) {
        this.mListInteractor = mListInteractor;
        this.mFavoriteTranslationInteractorInteractor = mFavoriteTranslationInteractorInteractor;
        mDisposables = new CompositeDisposable();
    }

    private void populateList() {
       /* mDisposables.add(mListInteractor.getTranslations()
                .filter(t -> !mFavoriteEnabled || t.isFavorite())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::add));*/
    }

    public void start() {
        populateList();
    }

    public void clickStar(Translation item, int index) {
       /* mDisposables.add(mFavoriteTranslationInteractorInteractor.toggleFavorite(item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translation -> {
                    if (mFavoriteEnabled) {
                        getViewState().remove(translation);
                    } else {
                        getViewState().updateTranslation(translation, index);
                    }
                }));*/
    }

    public void clickFavorite() {
        mFavoriteEnabled = !mFavoriteEnabled;
        getViewState().setFavoriteMenuIcon(mFavoriteEnabled);
        getViewState().reset();
        populateList();
    }

    public void addWord() {
        getViewState().startActivity(AddWordActivity.class);
    }

    public void wordAdded() {
        getViewState().reset();
        populateList();
    }

    public void menuCreated() {
        getViewState().setFavoriteMenuIcon(mFavoriteEnabled);
    }

    public void finish() {
        mDisposables.dispose();
    }
}
