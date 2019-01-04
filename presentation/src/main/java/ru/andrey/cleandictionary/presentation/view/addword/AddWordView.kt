package ru.andrey.cleandictionary.presentation.view.addword

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.andrey.cleandictionary.presentation.presenter.addword.MenuState

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddWordView : MvpView {

    fun setTranslation(translation: String)

    fun setAlternativeTranslations(translations: List<String>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(error: Throwable)

    fun setMenuState(menuState: MenuState)

    fun close()
}
