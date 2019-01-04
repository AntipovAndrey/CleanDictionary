package ru.andrey.data.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface MicrosoftApi {

    @POST("/dictionary/lookup?api-version=3.0")
    fun getTranslation(@Body word: MicrosoftApiRequest,
                       @Query("from") from: String,
                       @Query("to") langCodes: String): Single<MicrosoftApiResponse>
}
