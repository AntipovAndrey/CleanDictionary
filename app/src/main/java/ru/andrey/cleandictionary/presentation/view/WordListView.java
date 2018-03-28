package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;

import java.util.List;

import ru.andrey.cleandictionary.presentation.presenter.DictionaryItem;

/**
 * Created by antipov_an on 27.03.2018.
 */

public interface WordListView {

	List<DictionaryItem> getListFromAdapter();

	void setListListToAdapter(List<DictionaryItem> list);

	void setFavoriteMenuIcon(boolean activate);

	void startActivity(Class<? extends Activity> classActivity);

}
