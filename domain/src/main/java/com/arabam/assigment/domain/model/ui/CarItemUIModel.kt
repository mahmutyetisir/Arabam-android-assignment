package com.arabam.assigment.domain.model.ui

import com.arabam.assigment.domain.model.UIModel

typealias CarItemClickListener = (CarItemUIModel) -> Unit

data class CarItemUIModel(
    val id: Int?,
    val title: String?,
    val location: LocationUIModel?,
    val category: CategoryUIModel?,
    val modelName: String?,
    val price: Double?,
    val priceFormatted: String?,
    val date: String?,
    val dateFormatted: String?,
    val properties: List<PropertyUIModel>?,
    var photo: String?,
    var clickListener: CarItemClickListener? = null,
): UIModel