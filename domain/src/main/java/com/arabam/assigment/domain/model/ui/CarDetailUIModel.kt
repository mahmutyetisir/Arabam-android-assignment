package com.arabam.assigment.domain.model.ui

import com.arabam.assigment.domain.model.UIModel

data class CarDetailUIModel(val id: Int?,
                            val title: String?,
                            val location: LocationUIModel?,
                            val category: CategoryUIModel?,
                            val modelName: String?,
                            val price: Double?,
                            val priceFormatted: String?,
                            val date: String?,
                            val dateFormatted: String?,
                            var photos: List<String>?,
                            val properties: List<PropertyUIModel>?,
                            val text: String?,
                            val userInfo: UserUIModel?): UIModel