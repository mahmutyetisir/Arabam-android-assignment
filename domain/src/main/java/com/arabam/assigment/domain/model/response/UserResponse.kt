package com.arabam.assigment.domain.model.response

import com.arabam.assigment.domain.model.ResponseModel
import com.arabam.assigment.domain.model.ui.UserUIModel

data class UserResponse(
    val id: Int?,
    val nameSurname: String?,
    val phone: String?,
    val phoneFormatted: String?
) : ResponseModel<UserUIModel> {
    override fun toUIModel(): UserUIModel {
        return UserUIModel(id, nameSurname, phone, phoneFormatted)
    }
}