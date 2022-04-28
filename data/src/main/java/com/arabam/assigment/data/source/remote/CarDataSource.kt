package com.arabam.assigment.data.source.remote

import android.content.Context
import com.arabam.assigment.data.api.CarApi
import com.arabam.assigment.domain.model.response.CarDetailResponse
import com.arabam.assigment.domain.model.response.CarItemResponse
import com.arabam.assigment.domain.model.response.DataSourceResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.http.Query
import javax.inject.Inject

interface CarDataSource {

    suspend fun getDetail(id: Int): DataSourceResponse<CarDetailResponse>

    suspend fun getList(
        categoryId: Int? = null,
        minDate: String? = null,
        maxDate: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        sort: Int? = null,
        sortDirection: Int? = null,
        skip: Int? = null,
        take: Int? = null
    ): DataSourceResponse<List<CarItemResponse>>
}

class CarDataSourceImpl @Inject constructor(
    private val api: CarApi,
    @ApplicationContext context: Context
) : CarDataSource, BaseDataSource(context) {

    override suspend fun getDetail(id: Int): DataSourceResponse<CarDetailResponse> {
        return getResult {
            api.getDetail(id)
        }
    }

    override suspend fun getList(
        categoryId: Int?,
        minDate: String?,
        maxDate: String?,
        minYear: Int?,
        maxYear: Int?,
        sort: Int?,
        sortDirection: Int?,
        skip: Int?,
        take: Int?
    ): DataSourceResponse<List<CarItemResponse>> {
        return getResult {
            api.getList(categoryId, minDate, maxDate, minYear, maxYear, sort, sortDirection, skip)
        }
    }
}