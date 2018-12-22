package ru.andrey.cleandictionary.di.translation

import dagger.Component
import dagger.Subcomponent
import ru.andrey.cleandictionary.di.ApplicationComponent
import ru.andrey.cleandictionary.di.scope.Feature
import ru.andrey.cleandictionary.di.scope.Screen
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter
import ru.andrey.cleandictionary.presentation.presenter.DictionaryListPresenter

@Feature
@Component(dependencies = [ApplicationComponent::class],
        modules = [TranslationDataModule::class, TranslationModule::class])
interface TranslationComponent {

    fun presenter(): PresenterComponent

    @Screen
    @Subcomponent
    interface PresenterComponent {

        fun dictionaryListPresenter(): DictionaryListPresenter

        fun addWordPresenter(): AddWordPresenter
    }
}
