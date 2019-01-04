package ru.andrey.cleandictionary.di.translation.microsoft

import okhttp3.Interceptor
import ru.andrey.cleandictionary.di.translation.TranslationDataModule

class MicrosoftTranslationDataModule(private val apiKey: String)
    : TranslationDataModule("https://api.cognitive.microsofttranslator.com/") {

    override fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .addHeader("Ocp-Apim-Subscription-Key", apiKey)
                    .addHeader("Content-type", "application/json")
                    .build()

            chain.proceed(request)
        }

        interceptors.add(keyInterceptor)
        return interceptors
    }
}