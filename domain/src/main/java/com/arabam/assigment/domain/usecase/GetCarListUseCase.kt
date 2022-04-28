package com.arabam.assigment.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arabam.assigment.data.di.ArabamIODispatcher
import com.arabam.assigment.domain.model.query.ListQueries
import com.arabam.assigment.domain.model.ui.CarItemUIModel
import com.arabam.assigment.domain.repository.CarRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCarListUseCase @Inject constructor(
    private val repository: CarRepository,
    @ArabamIODispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(params: Params): Flow<PagingData<CarItemUIModel>> {
        return withContext(dispatcher) {
            val query = ListQueries(
                take = params.take,
                categoryId = params.categoryId,
                maxYear = params.maxYear,
                minYear = params.minYear,
                sort = params.sort,
                sortDirection = params.sortDirection
            )
            repository.getList(
                query
            ).map { pagingData ->
                pagingData.map {
                    it.toUIModel()
                }
            }
        }
    }

    data class Params(
        val categoryId: Int? = null,
        val minYear: Int? = null,
        val maxYear: Int? = null,
        val sort: Int? = null,
        val sortDirection: Int? = null,
        val take: Int? = null
    )
}