package ru.andrey.cleandictionary.data.network.translation;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.andrey.cleandictionary.model.PostModel;
import ru.andrey.cleandictionary.model.Translation;


public class YandexTranslate implements TranslationService {


    private static final String API_KEY =
            "trnsl.1.1.20180324T165001Z.898f85a4f411b333.ea8deb61604ca18ca90d6dd7fe5acda46980f77b";

    private static final String BASE_URL = "https://translate.yandex.net/";

    private static YandexApi sApi;
    private static Retrofit sRetrofit;

    static {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sApi = sRetrofit.create(YandexApi.class);
    }

    @Override
    public Single<Translation> getTranslation(Translation word) {
        return Single.defer(() -> {
            doTranslation(word);
            return Single.just(word);
        }).subscribeOn(Schedulers.io());
    }

    private void doTranslation(Translation word) throws IOException {
        final String langCodesFromTo =
                word.getLanguageFrom().getLanguageCode() + "-" + word.getLanguageTo().getLanguageCode();
        Response<PostModel> response = sApi.getData(API_KEY,
                word.getWord(),
                langCodesFromTo).execute();
        final PostModel body = response.body();
        final String result = body.getText().get(0);
        word.setTranslation(result);
    }
}
