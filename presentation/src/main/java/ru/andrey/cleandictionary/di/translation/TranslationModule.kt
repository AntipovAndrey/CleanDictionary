package ru.andrey.cleandictionary.di.translation

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.andrey.cleandictionary.di.scope.Feature
import ru.andrey.data.api.MicrosoftApi
import ru.andrey.data.api.YandexApi
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.repository.MicrosoftTranslationService
import ru.andrey.data.repository.TranslationRepositoryImpl
import ru.andrey.data.repository.YandexTranslationService
import ru.andrey.domain.interactor.FavoriteInteractor
import ru.andrey.domain.interactor.TranslationInteractor
import ru.andrey.domain.interactor.TranslationListInteractor
import ru.andrey.domain.repository.TranslationRepository
import ru.andrey.domain.repository.TranslationService

@Module
open class TranslationModule(val useMicrosoft: Boolean) {

    @Provides
    @Feature
    fun provideTranslationRepository(database: TranslationDatabase): TranslationRepository {
        return TranslationRepositoryImpl(database)
    }

    @Provides
    @Feature
    open fun provideTranslationService(retrofit: Retrofit): TranslationService {
        if (useMicrosoft) {
            return MicrosoftTranslationService(retrofit.create(MicrosoftApi::class.java))
        }
        return YandexTranslationService(retrofit.create(YandexApi::class.java))
    }

    @Feature
    @Provides
    fun provideTranslationInteractor(service: TranslationService): TranslationInteractor {
        return TranslationInteractor(Schedulers.io(), service)
    }

    @Feature
    @Provides
    fun provideTranslationsListInteractor(repo: TranslationRepository): TranslationListInteractor {
        return TranslationListInteractor(Schedulers.io(), repo)
    }

    @Feature
    @Provides
    fun provideFavoriteInteractor(repo: TranslationRepository): FavoriteInteractor {
        return FavoriteInteractor(Schedulers.io(), repo)
    }
}
