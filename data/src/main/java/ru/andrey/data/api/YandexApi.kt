package ru.andrey.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexApi {

    @GET("/api/v1.5/tr.json/translate")
    fun getTranslation(@Query("text") word: String,
                       @Query("lang") langCodes: String): Single<TranslationResponseModel>
}
