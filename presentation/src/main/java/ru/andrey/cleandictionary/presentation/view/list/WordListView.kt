package ru.andrey.cleandictionary.presentation.view.list

import android.app.Activity

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

import ru.andrey.cleandictionary.presentation.dto.TranslationDto

@StateStrategyType(AddToEndSingleStrategy::class)
interface WordListView : MvpView {

    fun setFavoriteMenuIcon(activate: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun startActivity(classActivity: Class<out Activity>)

    fun show(items: List<TranslationDto>)
}
