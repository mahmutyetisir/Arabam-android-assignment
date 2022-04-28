package com.arabam.assigment.data.api

import com.arabam.assigment.domain.model.response.CarDetailResponse
import com.arabam.assigment.domain.model.response.CarItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApi {

    @GET("listing")
    suspend fun getList(
        @Query("categoryId") categoryId: Int?,
        @Query("minDate") minDate: String?,
        @Query("maxDate") maxDate: String?,
        @Query("minYear") minYear: Int?,
        @Query("maxYear") maxYear: Int?,
        @Query("sort") sort: Int? = 0,
        @Query("sortDirection") sortDirection: Int? = 0,
        @Query("skip") skip: Int? = 0,
        @Query("take") take: Int = 20
    ): Response<List<CarItemResponse>>

    @GET("detail")
    suspend fun getDetail(@Query("id") id: Int): Response<CarDetailResponse>
}