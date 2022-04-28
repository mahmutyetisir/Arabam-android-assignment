package com.arabam.assigment.domain.model

import androidx.annotation.IntDef
import com.arabam.assigment.domain.model.SortType.Companion.DATE
import com.arabam.assigment.domain.model.SortType.Companion.PRICE
import com.arabam.assigment.domain.model.SortType.Companion.YEAR

@IntDef(
    PRICE,
    DATE,
    YEAR
)
@Retention(AnnotationRetention.SOURCE)
annotation class SortType() {
    companion object {
        const val PRICE = 0
        const val DATE = 1
        const val YEAR = 2
    }
}
