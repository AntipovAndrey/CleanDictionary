package ru.andrey.cleandictionary.presentation.presenter.list

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.andrey.cleandictionary.presentation.dto.TranslationDto
import ru.andrey.cleandictionary.presentation.view.list.WordListView
import ru.andrey.domain.interactor.FavoriteInteractor
import ru.andrey.domain.interactor.TranslationListInteractor
import javax.inject.Inject

@InjectViewState
class WordListPresenter @Inject
constructor(private val listInteracotor: TranslationListInteractor,
            private val favoriteInteractor: FavoriteInteractor) : MvpPresenter<WordListView>() {

    private var favoriteEnabled = false

    private val disposables = CompositeDisposable()
    private var listDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        populateList()
        viewState.setFavoriteMenuIcon(favoriteEnabled)
    }

    fun clickStar(id: Int) {
        favoriteInteractor.toggleFavorite(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
                .also { disposables.add(it) }
    }

    fun itemSwiped(id: Int) {
        listInteracotor.deleteWord(id)
                .subscribe()
                .also { disposables.add(it) }
    }

    fun clickFavorite() {
        favoriteEnabled = !favoriteEnabled
        viewState.setFavoriteMenuIcon(favoriteEnabled)
        populateList()
    }

    fun addWord() {
        viewState.openAddWord()
    }

    fun menuCreated() {
        viewState.setFavoriteMenuIcon(favoriteEnabled)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        listDisposable?.dispose()
    }

    private fun populateList() {
        listDisposable?.dispose()
        listInteracotor.getTranslations()
                .switchMap { list ->
                    Observable.fromIterable(list)
                            .filter {
                                if (favoriteEnabled) {
                                    it.favorite
                                } else {
                                    true
                                }
                            }
                            .map { TranslationDto.fromModel(it) }
                            .toList()
                            .toObservable()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { all -> viewState.show(all) }
                .also { listDisposable = it }
    }
}
