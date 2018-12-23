package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.andrey.cleandictionary.presentation.dto.TranslationDto;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface WordListView extends MvpView {

    void setFavoriteMenuIcon(boolean activate);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startActivity(Class<? extends Activity> classActivity);

    void show(List<TranslationDto> items);
}
