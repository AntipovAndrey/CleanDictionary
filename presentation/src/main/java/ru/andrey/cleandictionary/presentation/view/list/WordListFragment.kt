package ru.andrey.cleandictionary.presentation.view.list

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.andrey.cleandictionary.App
import ru.andrey.cleandictionary.R
import ru.andrey.cleandictionary.presentation.dto.TranslationDto
import ru.andrey.cleandictionary.presentation.presenter.list.WordListPresenter
import ru.andrey.cleandictionary.presentation.view.addword.AddWordActivity


class WordListFragment : MvpAppCompatFragment(), WordListView {

    private lateinit var rootView: View
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var hint: TextView

    private lateinit var favoriteItem: MenuItem
    private var favoriteEnabled: Boolean = false

    private lateinit var wordAdapter: WordAdapter

    @InjectPresenter
    internal lateinit var listPresenter: WordListPresenter

    @ProvidePresenter
    internal fun providePresenter(): WordListPresenter {
        return (activity!!.application as App)
                .translationComponent
                .presenter()
                .wordListPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.word_list_fragment, container, false)
        findViews(view)
        setupToolbar()
        setupButtonListener(view)
        showHint()
        setupRecycler()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.word_list_menu, menu)
        favoriteItem = menu!!.getItem(0)
        listPresenter.menuCreated()
    }

    override fun setFavoriteMenuIcon(activate: Boolean) {
        favoriteEnabled = activate

        if (!::favoriteItem.isInitialized) {
            return
        }
        favoriteItem.setIcon(if (activate)
            R.drawable.ic_favorite_24dp
        else
            R.drawable.ic_favorite_border_24dp)
    }

    override fun openAddWord() {
        startActivityForResult(Intent(activity, AddWordActivity::class.java), 42)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 42 && resultCode == RESULT_OK) {
            recyclerView.postDelayed({
                recyclerView.smoothScrollToPosition(0)
            }, 250)
        }
    }

    override fun show(items: List<TranslationDto>) {
        if (items.isEmpty()) {
            if (favoriteEnabled) {
                hint.setText(R.string.fav_word_hint)
            } else {
                hint.setText(R.string.add_word_hint)
            }
            showHint()
            return
        }
        showRecyclerView()
        wordAdapter.submitList(items)
    }

    override fun showSnackBar() {
        Snackbar.make(rootView, R.string.word_was_removed, Snackbar.LENGTH_SHORT)
                .apply {
                    setAction(R.string.undo_string) { listPresenter.clickUndoRemoving() }
                    show()
                }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            listPresenter.clickFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupButtonListener(view: View) {
        view.findViewById<View>(R.id.add_button)
                .setOnClickListener { listPresenter.addWord() }
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        wordAdapter = WordAdapter({ listPresenter.clickStar(it) }, requireContext())
        recyclerView.adapter = wordAdapter
        val itemTouch = RecyclerItemTouch(0, ItemTouchHelper.LEFT) {
            listPresenter.itemSwiped(it.id)
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView)
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
    }

    private fun findViews(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        rootView = view.findViewById(R.id.root)
        recyclerView = view.findViewById(R.id.recycler_view)
        hint = view.findViewById(R.id.hint)
    }

    private fun showRecyclerView() {
        hint.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showHint() {
        hint.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }
}
