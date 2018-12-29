package ru.andrey.cleandictionary.presentation.view.addword

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.andrey.cleandictionary.presentation.presenter.addword.ButtonState

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddWordView : MvpView {

    fun updateTranslation(word: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun errorToast(error: String)

    fun setButtonState(buttonState: ButtonState)

    fun close()
}
