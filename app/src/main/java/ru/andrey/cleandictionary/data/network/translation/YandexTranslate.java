package ru.andrey.cleandictionary.data.network.translation;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrey.cleandictionary.model.Translation;


public class YandexTranslate implements TranslationService {

    @Override
    public void setWordToTranslate(Translation word) {
        System.out.println("Word set");
    }

    @Override
    public Single<String> getTranslation() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("stub");
            }
        })
                .subscribeOn(Schedulers.io())
                .single("def");
    }
}
