package ru.andrey.cleandictionary.presentation.view;

import android.app.Activity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.andrey.cleandictionary.presentation.dto.TranslationDto;
import ru.andrey.domain.model.Translation;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface WordListView extends MvpView {

    void setFavoriteMenuIcon(boolean activate);

    void startActivity(Class<? extends Activity> classActivity);

    void show(List<TranslationDto> items);

    void updateTranslation(Translation translation, int index);

    void remove(Translation item);
}
