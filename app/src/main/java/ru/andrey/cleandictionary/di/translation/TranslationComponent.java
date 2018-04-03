package ru.andrey.cleandictionary.di.translation;

import dagger.Component;
import ru.andrey.cleandictionary.di.app.AppComponent;
import ru.andrey.cleandictionary.presentation.presenter.AddWordPresenter;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryItem;
import ru.andrey.cleandictionary.presentation.presenter.DictionaryListPresenter;

@Component(dependencies = AppComponent.class, modules = {TranslationModule.class})
@TranslationScope
public interface TranslationComponent {
	void inject(DictionaryListPresenter presenter);

	void inject(AddWordPresenter presenter);

	void inject(DictionaryItem item);
}
