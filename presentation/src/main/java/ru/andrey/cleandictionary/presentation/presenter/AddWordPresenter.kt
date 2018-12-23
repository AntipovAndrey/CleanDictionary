package ru.andrey.cleandictionary.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.andrey.cleandictionary.presentation.view.addword.AddWordView
import ru.andrey.domain.interactor.TranslationInteractor
import ru.andrey.domain.interactor.TranslationsListInteractor
import ru.andrey.domain.model.Language
import ru.andrey.domain.model.Translation
import javax.inject.Inject

@InjectViewState
class AddWordPresenter @Inject
constructor(private val translationInteractor: TranslationInteractor,
            private val translationsListInteractor: TranslationsListInteractor) : MvpPresenter<AddWordView>() {

    private var inputText: String? = null
    private var translation: String? = null

    private lateinit var langFrom: String
    private lateinit var langTo: String

    private var translateDisaposable: Disposable? = null

    fun updateTranslation(term: String) {
        viewState.disableButton(true)

        inputText = term
        translateDisaposable?.dispose()

        if (!term.isEmpty()) {
            translateDisaposable = Single.just(term)
                    .doOnSuccess { viewState.showProgressBar(true) }
                    .flatMap { translateWord(it, Language.byCode(langFrom), Language.byCode(langTo)) }
                    .subscribe(this::translationSucceed, this::translationError)
        } else {
            viewState.updateTranslation("")
        }
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

    fun setLangFrom(langCode: String) {
        langFrom = langCode
    }

    fun setLangTo(langCode: String) {
        langTo = langCode
    }

    private fun translationSucceed(translated: String) {
        viewState.updateTranslation(translated)
        translation = translated
        viewState.showProgressBar(false)
        viewState.disableButton(false)
    }

    private fun translationError(error: Throwable) {
        viewState.errorToast("Error")
        viewState.showProgressBar(false)
        viewState.disableButton(true)
    }

    private fun translateWord(word: String, from: Language, to: Language): Single<String> {
        return translationInteractor
                .getTranslation(word, from, to)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
