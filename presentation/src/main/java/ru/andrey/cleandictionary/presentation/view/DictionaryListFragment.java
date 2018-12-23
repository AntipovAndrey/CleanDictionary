package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.dto.TranslationDto;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryListPresenter;

public class DictionaryListFragment extends MvpAppCompatFragment implements WordListView {
    public static final int WORD_ADDED_CODE = 1337;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private MenuItem mFavoriteItem;
    private boolean mFavoriteEnabled;

    private CompositeDisposable mDisposables;

    WordAdapter mWordAdapter;

    @InjectPresenter
    DictionaryListPresenter mListPresenter;

    @ProvidePresenter
    DictionaryListPresenter providePresenter() {
        return ((App) getActivity().getApplication())
                .getTranslationComponent()
                .presenter()
                .dictionaryListPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dictionary_list_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mProgressBar = view.findViewById(R.id.progress_bar);
        view.findViewById(R.id.add_button).setOnClickListener(v -> mListPresenter.addWord());
        showProgressBar();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mWordAdapter = new WordAdapter(mListPresenter::clickStar, requireContext());
        mRecyclerView.setAdapter(mWordAdapter);
        showRecyclerView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposables.dispose();
        mDisposables = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        mFavoriteItem = menu.getItem(0);
        setFavoriteMenuIcon(mFavoriteEnabled);
        mListPresenter.menuCreated();
    }


    @Override
    public void setFavoriteMenuIcon(boolean activate) {
        mFavoriteEnabled = activate;
        if (mFavoriteItem != null) {
            mFavoriteItem.setIcon(activate ?
                    R.drawable.ic_favorite_24dp : R.drawable.ic_favorite_border_24dp);
        }
    }

    @Override
    public void startActivity(Class<? extends Activity> classActivity) {
        startActivityForResult(new Intent(getActivity(), classActivity), WORD_ADDED_CODE);
    }

    @Override
    public void show(List<TranslationDto> items) {
        mWordAdapter.submitList(items);
        mRecyclerView.scrollToPosition(0);
        mRecyclerView.post(() -> {
            mRecyclerView.smoothScrollToPosition(0);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WORD_ADDED_CODE) {
            mListPresenter.wordAdded();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            mListPresenter.clickFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showRecyclerView() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }
}
