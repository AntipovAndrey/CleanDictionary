package ru.andrey.cleandictionary.di.translation

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import ru.andrey.cleandictionary.di.scope.Feature
import ru.andrey.data.db.TranslationDatabase
import ru.andrey.data.repository.TranslationRepositoryImpl
import ru.andrey.domain.interactor.FavoriteInteractor
import ru.andrey.domain.interactor.TranslationInteractor
import ru.andrey.domain.interactor.TranslationListInteractor
import ru.andrey.domain.repository.TranslationRepository
import ru.andrey.domain.repository.TranslationService

@Module
open class TranslationModule {

    @Provides
    @Feature
    fun provideTranslationRepository(database: TranslationDatabase): TranslationRepository {
        return TranslationRepositoryImpl(database)
    }

    @Provides
    @Feature
    open fun provideTranslationService(retrofit: Retrofit): TranslationService {
        TODO("Override it in subclass")
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
