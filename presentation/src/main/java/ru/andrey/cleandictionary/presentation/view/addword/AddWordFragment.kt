package ru.andrey.cleandictionary.presentation.view.addword

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxAdapterView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.andrey.cleandictionary.App
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.presentation.presenter.addword.AddWordPresenter
import ru.andrey.cleandictionary.presentation.presenter.addword.ButtonState
import java.util.concurrent.TimeUnit

class AddWordFragment : MvpAppCompatFragment(), AddWordView {

    private lateinit var rootView: View
    private lateinit var toolbar: Toolbar
    private lateinit var wordEditText: EditText
    private lateinit var translation: TextView
    private lateinit var addButton: FloatingActionButton
    private lateinit var retryButton: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private lateinit var langFromSpinner: Spinner
    private lateinit var langToSpinner: Spinner

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
        findViews(view)
        setupToolbar()
        if (savedInstanceState == null) {
            initSpinners()
        }
        setButtonState(ButtonState.NO)
        return view
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable = CompositeDisposable()
        setupButtonListeners()
        setupSpinnerListeners()
        setupTextListeners()
    }

    override fun onStop() {
        super.onStop()
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

    override fun setTranslations(translations: List<String>) {
        translation.text = translations[0]
    }

    override fun showError(error: Throwable) {
        Snackbar.make(rootView, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
    }

    override fun close() {
        activity!!.setResult(Activity.RESULT_OK)
        activity!!.finish()
    }

    private fun setupButtonListeners() {
        addButton.setOnClickListener { presenter.addWord() }
        retryButton.setOnClickListener { presenter.retry() }
    }

    private fun initSpinners() {
        val items = resources.getStringArray(R.array.languages_spinner_items)
        langFromSpinner.setSelection(0)
        presenter.setLangFrom(items[0])
        langToSpinner.setSelection(1)
        presenter.setLangTo(items[1])
    }

    private fun setupToolbar() {
        toolbar.setTitle(R.string.add_new_word)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    private fun findViews(view: View) {
        rootView = view.findViewById(R.id.root)
        toolbar = view.findViewById(R.id.toolbar)
        wordEditText = view.findViewById(R.id.word_edit_text)
        translation = view.findViewById(R.id.translation_text_view)
        addButton = view.findViewById(R.id.add_word)
        progressBar = view.findViewById(R.id.progressBar)
        retryButton = view.findViewById(R.id.retry)
        langFromSpinner = view.findViewById(R.id.lang_from_spinner)
        langToSpinner = view.findViewById(R.id.lang_to_spinner)
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

    private fun setupSpinnerListeners() {
        val items = resources.getStringArray(R.array.languages_spinner_items)
        RxAdapterView.itemSelections(langFromSpinner)
                .skip(1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { items[it] }
                .subscribe { presenter.setLangFrom(it) }
                .also { compositeDisposable.add(it) }

        RxAdapterView.itemSelections(langToSpinner)
                .skip(1)
                .subscribeOn(AndroidSchedulers.mainThread())
                .map { items[it] }
                .subscribe { presenter.setLangTo(it) }
                .also { compositeDisposable.add(it) }
    }

    private fun setupTextListeners() {
        RxTextView.textChanges(wordEditText)
                .skip(1)
                .debounce(100, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { presenter.setWord(it) }
                .also { compositeDisposable.add(it) }
    }
}
