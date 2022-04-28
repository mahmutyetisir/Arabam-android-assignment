package com.arabam.assigment.domain.model.ui

import com.arabam.assigment.domain.model.UIModel

data class LocationUIModel(val cityName: String?, val townName: String?) : UIModel {

    fun toUIFormatted(): String {
        return "$cityName - $townName"
    }
}