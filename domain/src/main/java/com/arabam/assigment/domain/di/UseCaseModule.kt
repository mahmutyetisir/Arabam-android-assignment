package com.arabam.assigment.domain.di

import com.arabam.assigment.data.di.ArabamIODispatcher
import com.arabam.assigment.domain.repository.CarRepository
import com.arabam.assigment.domain.usecase.GetCarDetailUseCase
import com.arabam.assigment.domain.usecase.GetCarListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun bindGetCarDetailUseCase(
        repository: CarRepository,
        @ArabamIODispatcher dispatcher: CoroutineDispatcher
    ): GetCarDetailUseCase {
        return GetCarDetailUseCase(repository, dispatcher)
    }

    @Provides
    @Singleton
    fun bindGetCarListUseCase(
        repository: CarRepository,
        @ArabamIODispatcher dispatcher: CoroutineDispatcher
    ): GetCarListUseCase {
        return GetCarListUseCase(repository, dispatcher)
    }
}