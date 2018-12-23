package ru.andrey.cleandictionary.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import ru.andrey.cleandictionary.presentation.dto.TranslationDto
import ru.andrey.cleandictionary.presentation.view.addword.AddWordActivity
import ru.andrey.cleandictionary.presentation.view.list.WordListView
import ru.andrey.domain.interactor.FavoriteInteractor
import ru.andrey.domain.interactor.TranslationsListInteractor
import javax.inject.Inject

@InjectViewState
class DictionaryListPresenter @Inject
constructor(private val listInteracotor: TranslationsListInteractor,
            private val favoriteInteractor: FavoriteInteractor) : MvpPresenter<WordListView>() {

    private var mFavoriteEnabled = false

    private val mDisposables = CompositeDisposable()

    private val repopulateSubject = PublishSubject.create<Any>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        populateList()
        viewState.setFavoriteMenuIcon(mFavoriteEnabled)
    }

    fun clickStar(id: Int) {
        favoriteInteractor.toggleFavorite(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { any -> repopulateSubject.onNext(any) }
                .also { mDisposables.add(it) }
    }

    fun itemSwiped(id: Int) {
        listInteracotor.deleteWord(id)
                .subscribe { repopulateSubject.onNext(repopulateSubject) }
                .also { mDisposables.add(it) }
    }

    fun clickFavorite() {
        mFavoriteEnabled = !mFavoriteEnabled
        viewState.setFavoriteMenuIcon(mFavoriteEnabled)
        repopulateSubject.onNext(repopulateSubject)
    }

    fun addWord() {
        viewState.startActivity(AddWordActivity::class.java)
    }

    fun wordAdded() {
        repopulateSubject.onNext(repopulateSubject)
    }

    fun menuCreated() {
        viewState.setFavoriteMenuIcon(mFavoriteEnabled)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposables.dispose()
    }

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
                .toObservable()
                .repeatWhen { handler -> handler.flatMap { repopulateSubject } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { all -> viewState.show(all) })
    }
}
