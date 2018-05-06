package ru.andrey.cleandictionary.di.translation;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.domain.global.TranslationRepository;
import ru.andrey.cleandictionary.domain.global.TranslationService;
import ru.andrey.cleandictionary.domain.interactor.favorite.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.interactor.favorite.FavoriteTranslationInteractorImpl;
import ru.andrey.cleandictionary.domain.interactor.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.interactor.translation.TranslationInteractorImpl;
import ru.andrey.cleandictionary.domain.interactor.translationlist.TranslationsListInteractor;
import ru.andrey.cleandictionary.domain.interactor.translationlist.TranslationsListInteractorImpl;

@Module
public class TranslationModule {

    @Provides
    @TranslationScope
    FavoriteTranslationInteractor
    provideFavoriteTranslationInteractor(TranslationInteractor interactor) {
        return new FavoriteTranslationInteractorImpl(interactor);
    }

    @Provides
    @TranslationScope
    TranslationInteractor
    provideTranslationInteractor(TranslationService service, TranslationRepository repository) {
        return new TranslationInteractorImpl(service, repository);
    }

    @Provides
    @TranslationScope
    TranslationsListInteractor provideTranslationsListInteractor(TranslationRepository repository) {
        return new TranslationsListInteractorImpl(repository);
    }
}
