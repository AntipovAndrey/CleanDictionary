package ru.andrey.cleandictionary.presentation.view.addword

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.andrey.cleandictionary.presentation.presenter.addword.ButtonState

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddWordView : MvpView {

    fun setTranslations(translations: List<String>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: Throwable)

    fun setButtonState(buttonState: ButtonState)

    fun close()
}
