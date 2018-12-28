package ru.andrey.cleandictionary.presentation.view.addword

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.andrey.cleandictionary.App
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter
import ru.andrey.cleandictionary.presentation.presenter.ButtonState
import java.util.concurrent.TimeUnit

class AddWordFragment : MvpAppCompatFragment(), AddWordView {

    private lateinit var wordEditText: EditText
    private lateinit var translation: TextView
    private lateinit var addButton: FloatingActionButton
    private lateinit var retryButton: FloatingActionButton
    private lateinit var progressBar: ProgressBar

    private lateinit var compositeDisposable: CompositeDisposable

    @InjectPresenter
    internal lateinit var presenter: AddWordPresenter

    @ProvidePresenter
    internal fun providePresenter(): AddWordPresenter {
        return (activity?.application as App)
                .translationComponent
                .presenter()
                .addWordPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.add_word_fragment, container, false)
        wordEditText = view.findViewById(R.id.word_edit_text)
        translation = view.findViewById(R.id.translation_text_view)
        addButton = view.findViewById(R.id.add_word)
        progressBar = view.findViewById(R.id.progressBar)
        retryButton = view.findViewById(R.id.retry)
        setButtonState(ButtonState.NO)
        val langFromSpinner = view.findViewById<Spinner>(R.id.lang_from_spinner)
        val langToSpinner = view.findViewById<Spinner>(R.id.lang_to_spinner)
        val items = resources.getStringArray(R.array.languages_spinner_items)

        compositeDisposable.addAll(
                RxAdapterView.itemSelections(langFromSpinner)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { i -> presenter.setLangFrom(items[i]) },

                RxAdapterView.itemSelections(langToSpinner)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { i -> presenter.setLangTo(items[i]) },

                RxTextView.textChanges(wordEditText)
                        .debounce(100, TimeUnit.MILLISECONDS)
                        .map { it.toString() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { presenter.updateTranslation(it) }
        )

        langFromSpinner.setSelection(0)
        langToSpinner.setSelection(1)

        presenter.setLangFrom(items[0])
        presenter.setLangTo(items[1])

        addButton.setOnClickListener { _ -> presenter.addWord() }
        retryButton.setOnClickListener { _ -> presenter.retry() }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun setButtonState(buttonState: ButtonState) {
        when (buttonState) {
            ButtonState.NO -> {
                showProgressBar(false)
                enableAddButton(false)
                enableRetry(false)
            }

            ButtonState.ADD -> {
                showProgressBar(false)
                enableAddButton(true)
                enableRetry(false)
            }

            ButtonState.LOADING -> {
                showProgressBar(true)
                enableAddButton(false)
                enableRetry(false)
            }

            ButtonState.RETRY -> {
                showProgressBar(false)
                enableAddButton(false)
                enableRetry(true)
            }
        }
    }

    override fun updateTranslation(word: String) {
        translation.text = word
    }

    override fun errorToast(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun close() {
        activity!!.setResult(Activity.RESULT_OK)
        activity!!.finish()
    }

    private fun showProgressBar(enabled: Boolean) {
        progressBar.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
    }

    private fun enableAddButton(enabled: Boolean) {
        addButton.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
        addButton.isEnabled = enabled
    }

    private fun enableRetry(enabled: Boolean) {
        retryButton.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
        retryButton.isEnabled = enabled
    }
}
