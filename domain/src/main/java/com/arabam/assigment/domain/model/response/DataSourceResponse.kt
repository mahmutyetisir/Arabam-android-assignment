package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.Resource
import com.arabam.assigment.domain.model.error.BaseError

sealed class DataSourceResponse<out T>: BaseResponse<T> {
    data class Success<out T>(val data: T) : DataSourceResponse<T>() {
        override fun handleWithResource(): Resource<T> = Resource.Success(data)
    }

    data class Error(val exception: BaseError) : DataSourceResponse<Nothing>() {
        override fun handleWithResource(): Resource<Nothing> = Resource.Error(exception)
    }
}