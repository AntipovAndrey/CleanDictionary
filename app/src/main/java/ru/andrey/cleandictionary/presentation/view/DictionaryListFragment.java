package ru.andrey.cleandictionary.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
		implements WordAdapter.OnItemClickListener {
	private RecyclerView mRecyclerView;
	private ProgressBar mProgressBar;

	DictionaryListPresenter mListPresenter = new DictionaryListPresenter();
	private Disposable mSubscribe;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.dictionary_list_fragment, container, false);
		mRecyclerView = view.findViewById(R.id.recycler_view);
		mProgressBar = view.findViewById(R.id.progress_bar);
		showProgressBar();
		final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		layoutManager.setStackFromEnd(true);
		layoutManager.setReverseLayout(true);
		mRecyclerView.setLayoutManager(layoutManager);
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		mListPresenter.getList()
				.subscribe(new SingleObserver<List<DictionaryItem>>() {
					@Override
					public void onSubscribe(Disposable d) {
						mSubscribe = d;
					}

					@Override
					public void onSuccess(List<DictionaryItem> dictionaryItems) {
						showRecyclerView();
						final WordAdapter adapter =
								new WordAdapter(dictionaryItems, DictionaryListFragment.this);
						mRecyclerView.setAdapter(adapter);
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
		mListPresenter.itemClicked(item, getContext());
	}
}
