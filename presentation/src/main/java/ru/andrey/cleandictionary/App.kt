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
import ru.andrey.cleandictionary.di.translation.TranslationModule
import ru.andrey.domain.model.Language

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    val translationComponent: TranslationComponent by lazy {

        val useMicrosoft = !BuildConfig.MICROSOFT_API_KEY.isBlank()
        val baseUrl: String
        val key: String

        if (useMicrosoft) {
            baseUrl = "https://api.cognitive.microsofttranslator.com/"
            key = BuildConfig.MICROSOFT_API_KEY
        } else {
            baseUrl = "https://translate.yandex.net/"
            key = BuildConfig.YANDEX_API_KEY
        }

        DaggerTranslationComponent.builder()
                .applicationComponent(appComponent)
                .translationDataModule(TranslationDataModule(baseUrl, key, useMicrosoft))
                .translationModule(TranslationModule(useMicrosoft))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        val repo = translationComponent.translationRepository()

        Completable.fromAction {
            repo.initLanguages(*Language.values().map(Language::languageCode).toTypedArray())
        }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}
