package ru.andrey.cleandictionary.di.app;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.di.Constants;

@Module
public class BuildConfigModule {

	private final String mYandexApiKey;

	public BuildConfigModule(String yandexApiKey) {
		mYandexApiKey = yandexApiKey;
	}

	@Provides
	@Named(Constants.API_KEY)
	String provideApiKey() {
		return mYandexApiKey;
	}
}
