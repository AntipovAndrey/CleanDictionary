package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.R;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItem;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryListPresenter;

public class DictionaryListFragment extends Fragment
		implements WordAdapter.OnItemClickListener, WordListView {
	public static final int WORD_ADDED_CODE = 1337;
	private RecyclerView mRecyclerView;
	private ProgressBar mProgressBar;

	DictionaryListPresenter mListPresenter = new DictionaryListPresenter(this);
	private Disposable mSubscribe;
	private WordAdapter mWordAdapter;
	private MenuItem mFavoriteItem;

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
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		refreshRecyclerView();
	}

	private void refreshRecyclerView() {
		mListPresenter.getList()
				.subscribe(new SingleObserver<List<DictionaryItem>>() {
					@Override
					public void onSubscribe(Disposable d) {
						mSubscribe = d;
					}

					@Override
					public void onSuccess(List<DictionaryItem> dictionaryItems) {
						showRecyclerView();
						mWordAdapter = new WordAdapter(dictionaryItems, DictionaryListFragment.this);
						Toast.makeText(getActivity(), "RV refreshed " + dictionaryItems.size(), Toast.LENGTH_SHORT).show();
						mRecyclerView.setAdapter(mWordAdapter);
					}

					@Override
					public void onError(Throwable e) {
						Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	public void onStop() {
		super.onStop();
		mSubscribe.dispose();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
		mFavoriteItem = menu.getItem(0);
		setFavoriteMenuIcon(false);
	}

	@Override
	public void setFavoriteMenuIcon(boolean activate) {
		mFavoriteItem.setIcon(activate ?
				R.drawable.ic_favorite_24dp : R.drawable.ic_favorite_border_24dp);
	}

	@Override
	public void startActivity(Class<? extends Activity> classActivity) {
		startActivityForResult(new Intent(getActivity(), classActivity), WORD_ADDED_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == WORD_ADDED_CODE) {
			refreshRecyclerView();
			Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClicked(DictionaryItem item) {
		mListPresenter.clickItem(item, getContext());
	}

	@Override
	public List<DictionaryItem> getListFromAdapter() {
		return mWordAdapter.getItemList();
	}

	@Override
	public void setListListToAdapter(List<DictionaryItem> list) {
		mWordAdapter.setList(list);
	}
}
