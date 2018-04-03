package ru.andrey.cleandictionary.di.translation;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;

@Module
public class TranslationModule {

	@Provides
	@TranslationScope
	public TranslationsListInteractor provideTranslationsListInteractor() {
		return new TranslationsListInteractor();
	}

	@Provides
	@TranslationScope
	public TranslationInteractor provideTranslationInteractor() {
		return new TranslationInteractor();
	}

	@Provides
	@TranslationScope
	public FavoriteTranslationInteractor provideFavoriteTranslationInteractor() {
		return new FavoriteTranslationInteractor();
	}
}
