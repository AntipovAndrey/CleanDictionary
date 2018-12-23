package ru.andrey.cleandictionary

import android.app.Application
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.andrey.cleandictionary.di.ApplicationComponent
import ru.andrey.cleandictionary.di.ApplicationModule
import ru.andrey.cleandictionary.di.DaggerApplicationComponent
import ru.andrey.cleandictionary.di.translation.DaggerTranslationComponent
import ru.andrey.cleandictionary.di.translation.TranslationComponent
import ru.andrey.cleandictionary.di.translation.TranslationDataModule

class App : Application() {

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

    override fun onCreate() {
        super.onCreate()
        val repo = translationComponent.translationRepository()

        Completable.fromAction { repo.initLanguages("ru", "en", "fi") }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}
