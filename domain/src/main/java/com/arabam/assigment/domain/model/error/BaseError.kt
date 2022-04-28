package com.arabam.assigment.domain.model.error

open class BaseError(private val errorMessage: String?, var throwable: Throwable? = null) :
    Throwable(errorMessage) {
}