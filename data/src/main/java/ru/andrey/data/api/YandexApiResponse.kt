package ru.andrey.data.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class YandexApiResponse {

    @SerializedName("text")
    @Expose
    lateinit var translations: List<String>
}
