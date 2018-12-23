package ru.andrey.cleandictionary.presentation.view.addword

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AddWordView : MvpView {

    fun showProgressBar(enabled: Boolean)

    fun updateTranslation(word: String)

    fun errorToast(error: String)

    fun disableButton(disabled: Boolean)

    fun close()
}
