package com.arabam.assigment.data.di

import com.arabam.assigment.data.source.remote.CarDataSource
import com.arabam.assigment.data.source.remote.CarDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCarDataSource(carDataSourceImpl: CarDataSourceImpl): CarDataSource
}