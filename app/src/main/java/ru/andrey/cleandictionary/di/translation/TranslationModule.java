package ru.andrey.cleandictionary.di.translation;

import dagger.Module;
import dagger.Provides;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;
import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.FavoriteTranslationInteractorImpl;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationInteractorImpl;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractor;
import ru.andrey.cleandictionary.domain.translation.TranslationsListInteractorImpl;

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
