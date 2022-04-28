package com.arabam.assigment.domain.model.error

sealed class DataSourceError(errorMessage: String?, throwable: Throwable? = null) :
    BaseError(errorMessage, throwable) {

    class InternalServerError(throwable: Throwable? = null) :
        DataSourceError("Internal Server Error", throwable)

    class EmptyListError(throwable: Throwable? = null) :
        DataSourceError("Empty List Error", throwable)

    class ParametersError(throwable: Throwable? = null) :
        DataSourceError("Parameters Error", throwable)
}