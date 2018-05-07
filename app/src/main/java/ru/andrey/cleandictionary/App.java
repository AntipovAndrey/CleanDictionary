package ru.andrey.cleandictionary;

import android.app.Application;

import ru.andrey.cleandictionary.di.app.AppComponent;
import ru.andrey.cleandictionary.di.app.AppModule;
import ru.andrey.cleandictionary.di.app.BuildConfigModule;
import ru.andrey.cleandictionary.di.app.DaggerAppComponent;
import ru.andrey.cleandictionary.di.translation.DaggerTranslationComponent;
import ru.andrey.cleandictionary.di.translation.TranslationComponent;
import ru.andrey.cleandictionary.di.translation.TranslationModule;

public class App extends Application {
	public static App instance;
	private AppComponent mAppComponent;
	private TranslationComponent mTranslationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}

	public AppComponent getAppComponent() {
		if (mAppComponent == null) {
			mAppComponent = DaggerAppComponent.builder()
					.appModule(new AppModule())
					.buildConfigModule(new BuildConfigModule(BuildConfig.YANDEX_API_KEY))
					.build();
		}
		return mAppComponent;
	}

	public TranslationComponent getTranslationComponent() {
		if (mTranslationComponent == null) {
			mTranslationComponent = DaggerTranslationComponent.builder()
					.appComponent(getAppComponent())
					.translationModule(new TranslationModule())
					.build();
		}
		return mTranslationComponent;
	}
}
