package ru.andrey.cleandictionary.di.app;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.data.network.translation.YandexTranslate;
import ru.andrey.cleandictionary.data.repository.InMemoryRepository;
import ru.andrey.cleandictionary.di.Constants;
import ru.andrey.cleandictionary.domain.global.TranslationRepository;
import ru.andrey.cleandictionary.domain.global.TranslationService;

@Module
public class AppModule {

	@Provides
	@Singleton
	TranslationRepository provideTranslationRepository() {
		return new InMemoryRepository();
	}

	@Provides
	@Singleton
	TranslationService providesTranslationService(@Named(Constants.API_KEY) String key) {
		return new YandexTranslate(key);
	}
}
