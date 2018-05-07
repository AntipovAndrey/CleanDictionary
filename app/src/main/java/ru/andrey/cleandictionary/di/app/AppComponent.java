package ru.andrey.cleandictionary.di.app;

import javax.inject.Singleton;

import dagger.Component;
import ru.andrey.cleandictionary.domain.global.TranslationRepository;
import ru.andrey.cleandictionary.domain.global.TranslationService;

@Component(modules = {AppModule.class, BuildConfigModule.class})
@Singleton
public interface AppComponent {

    TranslationRepository translationRepository();

    TranslationService translationService();
}
