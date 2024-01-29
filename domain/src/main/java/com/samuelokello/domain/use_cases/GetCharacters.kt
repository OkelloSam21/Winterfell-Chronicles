package com.samuelokello.domain.use_cases

import com.samuelokello.data.remote.util.Resource
import com.samuelokello.domain.model.WinterFellResponse
import com.samuelokello.domain.repository.WinterFellRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacters @Inject constructor(private val winterFellRepository: WinterFellRepository){

    suspend operator fun invoke(): Flow<List<WinterFellResponse>> = flow {
        emit(Resource.Loading(null))
        try {
            val response = winterFellRepository.getCharacters()
            emit(Resource.Success(data = response))
        }catch (e:Exception){
            emit(Resource.Error("error occurred"))
        }
    }
}