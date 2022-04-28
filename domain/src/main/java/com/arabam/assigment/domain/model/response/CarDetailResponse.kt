package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.CarDetailUIModel

data class CarDetailResponse(
    val id: Int?,
    val title: String?,
    val location: LocationResponse?,
    val category: CategoryResponse?,
    val modelName: String?,
    val price: Double?,
    val priceFormatted: String?,
    val date: String?,
    val dateFormatted: String?,
    val photos: List<String>?,
    val properties: List<PropertyResponse>,
    val text: String?,
    val userInfo: UserResponse?
) : ResponseModel<CarDetailUIModel> {

    override fun toUIModel(): CarDetailUIModel {

        return CarDetailUIModel(
            id,
            title,
            location = location?.toUIModel(),
            category = category?.toUIModel(),
            modelName,
            price,
            priceFormatted,
            date,
            dateFormatted,
            photos,
            properties = properties.map { it.toUIModel() },
            text,
            userInfo = userInfo?.toUIModel()
        )
    }
}