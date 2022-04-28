package com.arabam.assigment.domain.model

import androidx.annotation.IntDef
import com.arabam.assigment.domain.model.SortDirection.Companion.ASCENDING
import com.arabam.assigment.domain.model.SortDirection.Companion.DESCENDING

@IntDef(
    ASCENDING,
    DESCENDING
)
@Retention(AnnotationRetention.SOURCE)
annotation class SortDirection() {
    companion object {
        const val ASCENDING = 0
        const val DESCENDING = 1
    }
}
