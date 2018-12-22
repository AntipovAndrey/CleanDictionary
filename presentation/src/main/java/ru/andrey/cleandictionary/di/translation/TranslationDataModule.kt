package ru.andrey.cleandictionary.di.translation

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.andrey.cleandictionary.di.scope.Feature
import ru.andrey.data.api.YandexApi
import ru.andrey.data.db.TranslationDatabase


@Module
class TranslationDataModule(private val baseUrl: String,
                            private val apiKey: String) {

    @Feature
    @Provides
    fun provideRoomDatabase(context: Context): TranslationDatabase {
        return Room.databaseBuilder(context,
                TranslationDatabase::class.java,
                "translations_db").build()
    }

    @Feature
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
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

    @Feature
    @Provides
    fun provideRetrofit(interceptors: ArrayList<Interceptor>): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        interceptors.forEach { interceptor ->
            clientBuilder.addInterceptor(interceptor)
        }
        return Retrofit.Builder()
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
    }

    @Feature
    @Provides
    fun provideYandexApi(retrofit: Retrofit): YandexApi {
        return retrofit.create(YandexApi::class.java)
    }
}

