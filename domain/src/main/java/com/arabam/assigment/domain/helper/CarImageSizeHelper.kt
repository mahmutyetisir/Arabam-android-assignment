package com.arabam.assigment.domain.helper

fun String.updateImageUrlBySize(sizeType: ImageSize): String {
    return replace("{0}", sizeType.size)
}

sealed class ImageSize(val size: String) {
    object SmallOne : ImageSize("120x90")
    object SmallTwo : ImageSize("160x120")
    object MediumOne : ImageSize("240x180")
    object MediumTwo : ImageSize("580x435")
    object BigOne : ImageSize("800x600")
    object BigTwo : ImageSize("1920x1080")
}