package ru.andrey.cleandictionary.di.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.network.translation.YandexTranslate;
import ru.andrey.cleandictionary.data.repository.InMemoryRepository;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;

@Module
public class AppModule {

	@Provides
	@Singleton
	public TranslationRepository provideTranslationRepository() {
		return new InMemoryRepository();
	}

	@Provides
	@Singleton
	public TranslationService providesTranslationService() {
		return new YandexTranslate();
	}

}
