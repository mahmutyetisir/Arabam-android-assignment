package com.arabam.assigment.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arabam.assigment.data.source.remote.CarDataSource
import com.arabam.assigment.domain.model.error.DataSourceError
import com.arabam.assigment.domain.model.query.ListQueries
import com.arabam.assigment.domain.model.response.CarItemResponse
import com.arabam.assigment.domain.model.response.DataSourceResponse
import java.lang.Exception

class CarPagingDataSource(
    private val carDataSource: CarDataSource,
    private val queries: ListQueries
) :
    PagingSource<Int, CarItemResponse>() {

    override fun getRefreshKey(state: PagingState<Int, CarItemResponse>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CarItemResponse> {
        return try {
            var currentKey: Int? = params.key ?: 0
            when (val result = carDataSource.getList(
                skip = currentKey, take = queries.take,
                categoryId = queries.categoryId,
                maxYear = queries.maxYear,
                minYear = queries.minYear,
                sort = queries.sort,
                sortDirection = queries.sortDirection
            )) {
                is DataSourceResponse.Error -> {
                    currentKey = null
                    LoadResult.Error(
                        result.exception.throwable ?: PagingSourceException("Unknown Error")
                    )
                }
                is DataSourceResponse.Success -> {
                    result.data.let { list ->
                        if (currentKey == 0 && list.isEmpty()) {
                            LoadResult.Error(DataSourceError.EmptyListError(PagingSourceException("Empty List")))
                        } else {
                            if (list.isEmpty()) {
                                currentKey = null
                            }
                            LoadResult.Page(list, null, currentKey?.plus(20))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    class PagingSourceException(message: String?) :
        Throwable(message ?: PagingSourceException::class.java.simpleName)
}