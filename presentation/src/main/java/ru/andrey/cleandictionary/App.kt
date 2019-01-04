package ru.andrey.cleandictionary

import android.app.Application
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import ru.andrey.cleandictionary.di.ApplicationComponent
import ru.andrey.cleandictionary.di.ApplicationModule
import ru.andrey.cleandictionary.di.DaggerApplicationComponent
import ru.andrey.cleandictionary.di.translation.DaggerTranslationComponent
import ru.andrey.cleandictionary.di.translation.TranslationComponent
import ru.andrey.cleandictionary.di.translation.microsoft.MicrosoftTranslationDataModule
import ru.andrey.cleandictionary.di.translation.microsoft.MicrosoftTranslationModule
import ru.andrey.cleandictionary.di.translation.yandex.YandexTranslationDataModule
import ru.andrey.cleandictionary.di.translation.yandex.YandexTranslationModule
import ru.andrey.domain.model.Language

class App : Application() {

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    val translationComponent: TranslationComponent by lazy {

        val useMicrosoft = !BuildConfig.MICROSOFT_API_KEY.isBlank()

        val dataModule = if (useMicrosoft) {
            MicrosoftTranslationDataModule(BuildConfig.MICROSOFT_API_KEY)
        } else {
            YandexTranslationDataModule(BuildConfig.YANDEX_API_KEY)
        }

        val translationModule = if (useMicrosoft) {
            MicrosoftTranslationModule()
        } else {
            YandexTranslationModule()
        }
        DaggerTranslationComponent.builder()
                .applicationComponent(appComponent)
                .translationDataModule(dataModule)
                .translationModule(translationModule)
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
