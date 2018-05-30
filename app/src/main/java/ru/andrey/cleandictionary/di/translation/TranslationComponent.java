package ru.andrey.cleandictionary.di.translation;

import dagger.Component;
import ru.andrey.cleandictionary.di.app.AppComponent;
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryListPresenter;

@Component(dependencies = AppComponent.class, modules = {TranslationModule.class})
@TranslationScope
public interface TranslationComponent {

	AddWordPresenter getAddWordPresenter();

	DictionaryListPresenter getDictionaryListPresenter();
}
