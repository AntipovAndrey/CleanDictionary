package ru.andrey.cleandictionary.presentation.view.list

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    private lateinit var recyclerView: RecyclerView
    private lateinit var hint: TextView

    private var favoriteItem: MenuItem? = null
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
        val view = inflater.inflate(R.layout.dictionary_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        hint = view.findViewById(R.id.hint)
        view.findViewById<View>(R.id.add_button).setOnClickListener { listPresenter.addWord() }
        showHint()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        wordAdapter = WordAdapter({ listPresenter.clickStar(it) }, requireContext())
        recyclerView.adapter = wordAdapter
        val itemTouch = RecyclerItemTouch(0, ItemTouchHelper.LEFT) {
            listPresenter.itemSwiped(it.id)
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        favoriteItem = menu!!.getItem(0)
        setFavoriteMenuIcon(favoriteEnabled)
        listPresenter.menuCreated()
    }

    override fun setFavoriteMenuIcon(activate: Boolean) {
        favoriteEnabled = activate
        if (favoriteItem != null) {
            favoriteItem!!.setIcon(if (activate)
                R.drawable.ic_favorite_24dp
            else
                R.drawable.ic_favorite_border_24dp)
        }
    }

    override fun openAddWord() {
        startActivityForResult(Intent(activity, AddWordActivity::class.java), WORD_ADDED_CODE)
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
        recyclerView.scrollToPosition(0)
        recyclerView.post { recyclerView.smoothScrollToPosition(0) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == WORD_ADDED_CODE) {
            listPresenter.wordAdded()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite) {
            listPresenter.clickFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRecyclerView() {
        hint.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showHint() {
        hint.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    companion object {

        const val WORD_ADDED_CODE = 1337
    }
}
