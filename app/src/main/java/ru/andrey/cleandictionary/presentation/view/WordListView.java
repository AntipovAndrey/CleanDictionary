package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;

import java.util.List;

import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;


public interface WordListView {

	List<DictionaryItemPresenter> getListFromAdapter();

	void setFavoriteMenuIcon(boolean activate);

	void startActivity(Class<? extends Activity> classActivity);

	void add(DictionaryItemPresenter item);

	void reset();
}
