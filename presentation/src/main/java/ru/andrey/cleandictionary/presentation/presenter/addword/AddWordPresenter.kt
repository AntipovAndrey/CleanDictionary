package ru.andrey.cleandictionary.presentation.presenter.addword

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.andrey.cleandictionary.presentation.dto.WordDto
import ru.andrey.cleandictionary.presentation.view.addword.AddWordView
import ru.andrey.domain.interactor.TranslationInteractor
import ru.andrey.domain.interactor.TranslationListInteractor
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import javax.inject.Inject

@InjectViewState
class AddWordPresenter @Inject
constructor(private val translationInteractor: TranslationInteractor,
            private val translationsListInteractor: TranslationListInteractor) : MvpPresenter<AddWordView>() {

    private var inputText: String? = null
    private var translation: String? = null

    private lateinit var langFrom: String
    private lateinit var langTo: String


    private val wordSubject = BehaviorSubject.create<String>()
    private val langFromSubject = BehaviorSubject.create<String>()
    private val langToSubject = BehaviorSubject.create<String>()

    private val retrySubject = PublishSubject.create<Any>()

    private lateinit var translateDisposable: Disposable

    override fun onFirstViewAttach() {
        viewState.setButtonState(ButtonState.NO)
        Observables.combineLatest(wordSubject, langFromSubject, langToSubject, ::WordDto)
                .doOnNext { viewState.setButtonState(ButtonState.LOADING) }
                .switchMap {
                    translateWord(it)
                            .toObservable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .retryWhen { handler ->
                                handler.flatMap { error ->
                                    translationError(error)
                                    retrySubject
                                }
                            }
                }
                .subscribe(this::translationSucceed)
                .also { translateDisposable = it }
    }

    override fun onDestroy() {
        translateDisposable.dispose()
    }

    fun setWord(term: String) {
        inputText = term
        wordSubject.onNext(term)
    }

    fun setLangFrom(langCode: String) {
        langFrom = langCode
        langFromSubject.onNext(langCode)
    }

    fun setLangTo(langCode: String) {
        langTo = langCode
        langToSubject.onNext(langCode)
    }

    fun addWord() {
        translationsListInteractor.saveWord(Translation(
                word = inputText!!,
                translation = translation!!,
                languageFrom = Language.byCode(langFrom),
                languageTo = Language.byCode(langTo)
        )).subscribe()

        viewState.close()
    }

    fun retry() {
        retrySubject.onNext(retrySubject)
    }

    private fun translationSucceed(translated: String) {
        viewState.updateTranslation(translated)
        translation = translated
        viewState.setButtonState(ButtonState.ADD)
    }

    private fun translationError(error: Throwable) {
        viewState.errorToast("Error")
        viewState.setButtonState(ButtonState.RETRY)
    }

    private fun translateWord(wordDto: WordDto): Single<String> {
        return translationInteractor
                .getTranslation(wordDto.word,
                        Language.byCode(wordDto.from),
                        Language.byCode(wordDto.to))
    }
}
