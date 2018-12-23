package ru.andrey.cleandictionary.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddWordView extends MvpView {

    void showProgressBar(boolean enabled);

    void updateTranslation(String word);

    void errorToast(String error);

    void disableButton(boolean disabled);

    void close();
}
