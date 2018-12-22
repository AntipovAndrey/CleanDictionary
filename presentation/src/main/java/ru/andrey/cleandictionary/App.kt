package ru.andrey.cleandictionary

import android.app.Activity
import android.app.Application
import ru.andrey.cleandictionary.di.ApplicationComponent
import ru.andrey.cleandictionary.di.ApplicationModule
import ru.andrey.cleandictionary.di.DaggerApplicationComponent
import ru.andrey.cleandictionary.di.translation.DaggerTranslationComponent
import ru.andrey.cleandictionary.di.translation.TranslationComponent
import ru.andrey.cleandictionary.di.translation.TranslationDataModule

class App : Application() {
    val Activity.app: App
        get() = application as App

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    val translationComponent: TranslationComponent by lazy {
        DaggerTranslationComponent.builder()
                .applicationComponent(appComponent)
                .translationDataModule(TranslationDataModule("https://translate.yandex.net/", BuildConfig.YANDEX_API_KEY))
                .build()
    }
}
