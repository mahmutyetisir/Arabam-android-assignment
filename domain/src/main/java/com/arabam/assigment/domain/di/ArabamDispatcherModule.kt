package com.arabam.assigment.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArabamDispatcherModule {

    @Singleton
    @ArabamDefaultDispatcher
    @Provides
    fun providesArabamDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Singleton
    @ArabamIODispatcher
    @Provides
    fun providesArabamIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @ArabamMainDispatcher
    @Provides
    fun providesArabamMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ArabamDefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ArabamIODispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ArabamMainDispatcher