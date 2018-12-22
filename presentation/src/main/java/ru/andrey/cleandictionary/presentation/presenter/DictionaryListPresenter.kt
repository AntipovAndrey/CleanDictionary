package ru.andrey.cleandictionary.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.andrey.cleandictionary.presentation.dto.TranslationDto
import ru.andrey.cleandictionary.presentation.view.AddWordActivity
import ru.andrey.cleandictionary.presentation.view.WordListView
import ru.andrey.domain.interactor.FavoriteInteractor
import ru.andrey.domain.interactor.TranslationsListInteractor
import javax.inject.Inject

@InjectViewState
class DictionaryListPresenter @Inject
constructor(private val listInteracotor: TranslationsListInteractor,
            private val favoriteInteractor: FavoriteInteractor) : MvpPresenter<WordListView>() {

    private var mFavoriteEnabled: Boolean = false

    private val mDisposables: CompositeDisposable = CompositeDisposable()

    private fun populateList() {
        mDisposables.add(listInteracotor.getTranslations()
                .flatMapObservable { list -> Observable.fromIterable(list) }
                .filter {
                    if (mFavoriteEnabled) {
                        it.favorite
                    } else {
                        true
                    }
                }
                .map { TranslationDto.fromModel(it) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { all -> viewState.show(all) })
    }

    fun start() {
        populateList()
    }

    fun clickStar(item: TranslationDto, index: Int) {
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

    fun clickFavorite() {
        mFavoriteEnabled = !mFavoriteEnabled
        viewState.setFavoriteMenuIcon(mFavoriteEnabled)
//        viewState.reset()
        populateList()
    }

    fun addWord() {
        viewState.startActivity(AddWordActivity::class.java)
    }

    fun wordAdded() {
//        viewState.reset()
        populateList()
    }

    fun menuCreated() {
        viewState.setFavoriteMenuIcon(mFavoriteEnabled)
    }

    fun finish() {
        mDisposables.dispose()
    }
}
