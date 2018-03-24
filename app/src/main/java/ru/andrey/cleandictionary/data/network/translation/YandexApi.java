package ru.andrey.cleandictionary.data.network.translation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andrey.cleandictionary.model.PostModel;

public interface YandexApi {
    @GET("/api/v1.5/tr.json/translate")
    Call<PostModel> getData(@Query("key") String apiKey,
                            @Query("text") String word,
                            @Query("lang") String langCodes);
}
