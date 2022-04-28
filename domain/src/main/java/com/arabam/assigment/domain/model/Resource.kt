package com.arabam.assigment.domain.model

import com.arabam.assigment.domain.model.error.BaseError

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: BaseError) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}