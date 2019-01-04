package ru.andrey.cleandictionary.di.translation.yandex

import okhttp3.Interceptor
import ru.andrey.cleandictionary.di.translation.TranslationDataModule

class YandexTranslationDataModule(private val apiKey: String)
    : TranslationDataModule("https://translate.yandex.net/") {

    override fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }
}