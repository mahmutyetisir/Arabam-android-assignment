package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.CarItemUIModel

data class CarItemResponse(
    val id: Int?,
    val title: String?,
    val location: LocationResponse?,
    val category: CategoryResponse?,
    val modelName: String?,
    val price: Double?,
    val priceFormatted: String?,
    val date: String?,
    val dateFormatted: String?,
    val photo: String?,
    val properties: List<PropertyResponse>?
) : ResponseModel<CarItemUIModel> {

    override fun toUIModel(): CarItemUIModel {
        return CarItemUIModel(
            id,
            title,
            location?.toUIModel(),
            category?.toUIModel(),
            modelName,
            price,
            priceFormatted,
            date,
            dateFormatted,
            properties?.map { it.toUIModel() },
            photo
        )
    }
}