package com.arabam.assigment.domain.model.query

data class ListQueries(
    val categoryId: Int? = null,
    val minDate: String? = null,
    val maxDate: String? = null,
    val minYear: Int? = null,
    val maxYear: Int? = null,
    val sort: Int? = null,
    val sortDirection: Int? = null,
    val skip: Int? = null,
    val take: Int? = null
)
