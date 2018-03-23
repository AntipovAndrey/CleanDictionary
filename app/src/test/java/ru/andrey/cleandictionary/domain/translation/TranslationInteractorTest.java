package ru.andrey.cleandictionary.domain.translation;

import org.junit.Test;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.andrey.cleandictionary.model.Language;
import ru.andrey.cleandictionary.model.Translation;

import static org.junit.Assert.*;

/**
 * Created by andrey on 22.03.18.
 */
public class TranslationInteractorTest {

    @Test
    public void testGetTranslate() throws Exception {
        Translation t = new Translation("Text", Language.ENGLISH, Language.RUSSIAN);

        TranslationInteractor interactor = new TranslationInteractor(t);

        interactor.getTranslation(new Observer<Translation>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println(d);
            }

            @Override
            public void onNext(Translation translation) {
                System.out.printf(translation.getTranslation());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}