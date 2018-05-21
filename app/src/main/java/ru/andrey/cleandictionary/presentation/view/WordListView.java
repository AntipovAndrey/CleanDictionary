package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.andrey.cleandictionary.presentation.presenter.DictionaryItemPresenter;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface WordListView extends MvpView {

	void setFavoriteMenuIcon(boolean activate);

	void startActivity(Class<? extends Activity> classActivity);

	void add(DictionaryItemPresenter item);

	void remove(DictionaryItemPresenter item);

	void reset();
}
