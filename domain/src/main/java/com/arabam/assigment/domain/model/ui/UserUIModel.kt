package com.arabam.assigment.domain.model.ui

import com.arabam.assigment.domain.model.UIModel

data class UserUIModel(
    val id: Int?,
    val nameSurname: String?,
    val phone: String?,
    val phoneFormatted: String?
) : UIModel