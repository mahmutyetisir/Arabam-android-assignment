package com.arabam.assigment.data.di

import com.arabam.assigment.data.repository.CarRepositoryImpl
import com.arabam.assigment.domain.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCarRepository(carRepositoryImpl: CarRepositoryImpl): CarRepository
}