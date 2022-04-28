package com.arabam.assigment.domain.usecase

import com.arabam.assigment.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

abstract class ArabamUseCase<in Params, Type> constructor(
    private val dispatcher: CoroutineDispatcher
) {

    abstract suspend fun getExecutable(params: Params): Resource<Type>

    suspend operator fun invoke(params: Params): Flow<Resource<Type>> {
        return withContext(dispatcher) {
            flow {
                emit(Resource.Loading)
                emit(getExecutable(params))
            }
        }
    }
}