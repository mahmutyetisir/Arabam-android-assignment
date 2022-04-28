package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.LocationUIModel

data class LocationResponse(val cityName: String?, val townName: String?) : ResponseModel<LocationUIModel> {

    override fun toUIModel(): LocationUIModel {
        return LocationUIModel(cityName, townName)
    }

}