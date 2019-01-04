package ru.andrey.cleandictionary.presentation.view.addword

import android.app.Activity
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.EditText
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
import ru.andrey.cleandictionary.presentation.presenter.addword.MenuState
import java.util.concurrent.TimeUnit

class AddWordFragment : MvpAppCompatFragment(), AddWordView {

    private lateinit var rootView: View
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var wordEditText: EditText
    private lateinit var translation: TextView
    private lateinit var langFromSpinner: Spinner
    private lateinit var langToSpinner: Spinner

    private lateinit var menuAction: MenuItem

    private lateinit var addWordAdapter: AddWordAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.add_word_fragment, container, false)
        findViews(view)
        setupToolbar()
        setupRecycler()
        if (savedInstanceState == null) {
            initSpinners()
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_word_menu, menu)
        menuAction = menu.getItem(0)
        presenter.menuCreated()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_action) {
            presenter.menuClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable = CompositeDisposable()
        setupSpinnerListeners()
        setupTextListeners()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    override fun setMenuState(menuState: MenuState) {
        if (!::menuAction.isInitialized) {
            return
        }
        when (menuState) {
            MenuState.NO -> menuAction.isVisible = false
            MenuState.ADD -> showMenuItemIcon(R.drawable.ic_add_24dp)
            MenuState.LOADING -> menuAction.isVisible = false
            MenuState.RETRY -> showMenuItemIcon(R.drawable.ic_refresh_24dp)
        }
    }

    override fun setTranslation(translation: String) {
        this.translation.text = translation
    }

    override fun setAlternativeTranslations(translations: List<String>) {
        addWordAdapter.submitList(translations)
    }

    override fun showError(error: Throwable) {
        Snackbar.make(rootView, R.string.error_no_connection, Snackbar.LENGTH_SHORT).show()
    }

    override fun close() {
        activity!!.setResult(Activity.RESULT_OK)
        activity!!.finish()
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        addWordAdapter = AddWordAdapter { presenter.selectTranslation(it) }
        recyclerView.adapter = addWordAdapter
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
        recyclerView = view.findViewById(R.id.recycler_view)
        wordEditText = view.findViewById(R.id.word_edit_text)
        translation = view.findViewById(R.id.translation_text_view)
        langFromSpinner = view.findViewById(R.id.lang_from_spinner)
        langToSpinner = view.findViewById(R.id.lang_to_spinner)
    }

    private fun showMenuItemIcon(@DrawableRes id: Int) {
        menuAction.isVisible = true
        menuAction.setIcon(id)
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
