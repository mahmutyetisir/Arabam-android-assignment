package com.arabam.assigment.domain.usecase

import com.arabam.assigment.data.di.ArabamIODispatcher
import com.arabam.assigment.domain.model.Resource
import com.arabam.assigment.domain.model.response.DataSourceResponse
import com.arabam.assigment.domain.model.ui.CarDetailUIModel
import com.arabam.assigment.domain.repository.CarRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCarDetailUseCase @Inject constructor(
    private val repository: CarRepository,
    @ArabamIODispatcher private val dispatcher: CoroutineDispatcher
) : ArabamUseCase<Int, CarDetailUIModel>(dispatcher) {

    override suspend fun getExecutable(params: Int): Resource<CarDetailUIModel> {
        return when (val response = repository.getDetail(params)) {
            is DataSourceResponse.Error -> {
                Resource.Error(response.exception)
            }
            is DataSourceResponse.Success -> {
                Resource.Success(response.data.toUIModel())
            }
        }
    }
}