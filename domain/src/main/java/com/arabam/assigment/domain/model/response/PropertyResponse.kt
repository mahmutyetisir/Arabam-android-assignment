package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.PropertyUIModel

data class PropertyResponse(val name: String?, val value: String?) : ResponseModel<PropertyUIModel> {
    override fun toUIModel(): PropertyUIModel {
        return PropertyUIModel(name, value)
    }
}
