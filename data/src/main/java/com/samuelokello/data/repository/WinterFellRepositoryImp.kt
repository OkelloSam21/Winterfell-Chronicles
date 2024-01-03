package com.samuelokello.data.repository

import com.samuelokello.data.remote.ApiService
import com.samuelokello.data.remote.util.Resource
import com.samuelokello.domain.model.WinterFellResponse
import com.samuelokello.domain.repository.WinterFellRepository
import javax.inject.Inject

class WinterFellRepositoryImp @Inject constructor(private val apiService: ApiService) :
    WinterFellRepository {
    override suspend fun getCharacters(): Resource<List<WinterFellResponse>> {
        return try {
            val result = apiService.getCharacters()
            Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown error")
        }
    }

    override suspend fun getCharactersById(id: Int): Resource<WinterFellResponse> {
        return try {
            val result = apiService.getCharacterById(id)
            Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown error")
        }
    }
}

// import com.samuelokello.data.network.ApiService
// import com.samuelokello.data.network.util.SafeApiRequest
// import com.samuelokello.domain.model.WinterFellResponse
// import com.samuelokello.domain.repository.WinterFellRepository
// import retrofit2.Response
// import javax.inject.Inject

// class WinterFellRepositoryImp @Inject constructor(private val apiService: ApiService) :
//    WinterFellRepository, SafeApiRequest() {
//    override suspend fun getCharacter(): Response<List<WinterFellResponse>> {
//        val response = safeApiRequest { apiService.getCharacters()  }
//        return response.data
//    }
//
// }
