package com.arabam.assigment.domain.model

interface ResponseModel<T> {

    fun toUIModel(): T
}