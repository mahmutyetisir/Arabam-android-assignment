package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.CategoryUIModel

data class CategoryResponse(val id: Int?, val name: String?) : ResponseModel<CategoryUIModel> {

    override fun toUIModel(): CategoryUIModel {
        return CategoryUIModel(id, name)
    }
}
