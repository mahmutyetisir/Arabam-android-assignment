package com.arabam.assigment.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arabam.assigment.data.source.paging.CarPagingDataSource
import com.arabam.assigment.data.source.remote.CarDataSource
import com.arabam.assigment.domain.model.query.ListQueries
import com.arabam.assigment.domain.model.response.CarDetailResponse
import com.arabam.assigment.domain.model.response.CarItemResponse
import com.arabam.assigment.domain.model.response.DataSourceResponse
import com.arabam.assigment.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val dataSource: CarDataSource) : CarRepository {

    override suspend fun getDetail(id: Int): DataSourceResponse<CarDetailResponse> {
        return dataSource.getDetail(id)
    }

    override suspend fun getList(
        queries: ListQueries
    ): Flow<PagingData<CarItemResponse>> {
        return Pager(PagingConfig(pageSize = 100, initialLoadSize = 1)) {
            CarPagingDataSource(carDataSource = dataSource, queries)
        }.flow
    }
}