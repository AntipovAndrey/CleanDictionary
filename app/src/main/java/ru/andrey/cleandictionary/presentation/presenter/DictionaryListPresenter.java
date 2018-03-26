package ru.andrey.cleandictionary.presentation.presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;


public class DictionaryListPresenter {

	TranslationsListInteractor mInteractor = new TranslationsListInteractor();

	public Single<List<DictionaryItem>> getList() {
		return mInteractor.getTranslationList(AndroidSchedulers.mainThread());
	}

	public void itemClicked(DictionaryItem item, Context context) {

	}
}
