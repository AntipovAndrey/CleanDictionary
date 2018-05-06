package ru.andrey.cleandictionary.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.andrey.cleandictionary.data.network.translation.TranslationService;
import ru.andrey.cleandictionary.data.repository.TranslationRepository;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {

    TranslationRepository translationRepository();

    TranslationService translationService();
}
