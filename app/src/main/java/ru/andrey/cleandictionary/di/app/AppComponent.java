package ru.andrey.cleandictionary.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

	void inject(FavoriteTranslationInteractor interactor);

	void inject(TranslationInteractor interactor);

	void inject(TranslationsListInteractor interactor);

}
