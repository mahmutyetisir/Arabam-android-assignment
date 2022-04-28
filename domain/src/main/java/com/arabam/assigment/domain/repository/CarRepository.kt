package com.arabam.assigment.domain.repository

import androidx.paging.PagingData
import com.arabam.assigment.domain.model.query.ListQueries
import com.arabam.assigment.domain.model.response.CarDetailResponse
import com.arabam.assigment.domain.model.response.CarItemResponse
import com.arabam.assigment.domain.model.response.DataSourceResponse
import kotlinx.coroutines.flow.Flow

interface CarRepository {

    suspend fun getDetail(id: Int): DataSourceResponse<CarDetailResponse>

    suspend fun getList(
        queries: ListQueries
    ): Flow<PagingData<CarItemResponse>>
}