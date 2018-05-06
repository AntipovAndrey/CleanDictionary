package ru.andrey.cleandictionary.presentation.presenter;

import android.content.Context;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.domain.interactor.translationlist.TranslationsListInteractor;
import ru.andrey.cleandictionary.presentation.view.AddWordActivity;
import ru.andrey.cleandictionary.presentation.view.WordListView;


public class DictionaryListPresenter {

    @Inject
    TranslationsListInteractor mInteractor;

    private WordListView mWordListView;
    private boolean mFavoriteEnabled;

    private Disposable mDisposable;

    {
        App.instance.getTranslationComponent().inject(this);
    }

    public DictionaryListPresenter(WordListView wordListView) {
        mWordListView = wordListView;
    }

    public Observable<DictionaryItemPresenter> getList() {
        return mInteractor.getTranslations()
                .map(DictionaryItemPresenter::new)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void clickItem(DictionaryItemPresenter item, Context context) {

    }

    public void clickFavorite() {
        mFavoriteEnabled = !mFavoriteEnabled;
        if (mDisposable != null) {
            mDisposable.dispose();
        }

        Observable<DictionaryItemPresenter> itemsObservable = getList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(ignored -> {
                    mWordListView.setFavoriteMenuIcon(mFavoriteEnabled);
                    mWordListView.reset();
                });

        if (mFavoriteEnabled) {
            itemsObservable = itemsObservable
                    .filter(DictionaryItemPresenter::isFavorite);
        }

        mDisposable = itemsObservable
                .subscribe(mWordListView::add);
    }

    public void addWord() {
        mWordListView.startActivity(AddWordActivity.class);
    }
}
