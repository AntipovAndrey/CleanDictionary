package ru.andrey.cleandictionary.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule constructor(private val context: Context) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return context.applicationContext
    }
}
