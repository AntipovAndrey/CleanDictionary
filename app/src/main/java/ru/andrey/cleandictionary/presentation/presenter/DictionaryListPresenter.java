package ru.andrey.cleandictionary.presentation.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.domain.interactor.translationlist.TranslationsListInteractor;
import ru.andrey.cleandictionary.presentation.view.AddWordActivity;
import ru.andrey.cleandictionary.presentation.view.WordListView;

@InjectViewState
public class DictionaryListPresenter extends MvpPresenter<WordListView> {

    @Inject
    TranslationsListInteractor mInteractor;

    private boolean mFavoriteEnabled;

    private Disposable mDisposable;

    @Inject
    public DictionaryListPresenter() {
    }

    private void populateList() {
        mDisposable = mInteractor.getTranslations()
                .filter(t -> !mFavoriteEnabled || t.isFavorite())
                .map(DictionaryItemPresenter::new)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::add);
    }

    public void start() {
        populateList();
    }

    public void clickItem(DictionaryItemPresenter item, Context context) {

    }

    public void clickFavorite() {
        mFavoriteEnabled = !mFavoriteEnabled;
        if (mDisposable != null) {
            mDisposable.dispose();
        }

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

    public void clickStar(DictionaryItemPresenter i) {
        if (mFavoriteEnabled) {
            getViewState().remove(i);
        }
    }
}
