package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.Resource

interface BaseResponse<out T> {

    fun handleWithResource(): Resource<T>
}