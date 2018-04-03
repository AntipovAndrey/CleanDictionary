package ru.andrey.cleandictionary.presentation.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.App;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;
import ru.andrey.cleandictionary.presentation.view.AddWordActivity;
import ru.andrey.cleandictionary.presentation.view.WordListView;


public class DictionaryListPresenter {

	@Inject
	TranslationsListInteractor mInteractor;

	private WordListView mWordListView;
	private boolean mFavoriteEnabled;

	private List<DictionaryItem> mOriginalData;

	{
		App.instance.getTranslationComponent().inject(this);
	}

	public DictionaryListPresenter(WordListView wordListView) {
		mWordListView = wordListView;
	}

	public Single<List<DictionaryItem>> getList() {
		mOriginalData = null;
		return mInteractor.getTranslationList(AndroidSchedulers.mainThread());
	}

	public void clickItem(DictionaryItem item, Context context) {

	}

	public void clickFavorite() {
		mFavoriteEnabled = !mFavoriteEnabled;
		if (mOriginalData == null) {
			mOriginalData = mWordListView.getListFromAdapter();
		}
		List<DictionaryItem> items = mOriginalData;

		Single.just(items)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.map(l -> {
					if (mFavoriteEnabled) {
						List<DictionaryItem> itemList = new ArrayList<>(items.size());
						for (DictionaryItem item : items) {
							if (item.isFavorite()) {
								itemList.add(item);
							}
						}
						return itemList;
					}
					return l;
				})
				.subscribe(l -> {
					mWordListView.setListListToAdapter(l);
					mWordListView.setFavoriteMenuIcon(mFavoriteEnabled);
				});
	}

	public void addWord() {
		mWordListView.startActivity(AddWordActivity.class);
	}
}
