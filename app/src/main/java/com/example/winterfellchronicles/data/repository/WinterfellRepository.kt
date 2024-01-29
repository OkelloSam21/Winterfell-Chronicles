package com.example.winterfellchronicles.data.repository

import android.util.Log
import com.example.winterfellchronicles.data.remote.ApiService
import com.example.winterfellchronicles.data.remote.WinterFellCharacterDetailsResponse
import com.example.winterfellchronicles.data.remote.WinterFellResponse
import com.example.winterfellchronicles.utils.Resource

class WinterfellRepository(
    private val apiService: ApiService,
) {
    suspend fun getWinterfellCharacters(): Resource<List<WinterFellResponse>> {
        return try {
            val result = apiService.getCharacters()
            Log.d("WinterfellRepository", "getWinterFellCharactyers: $result")
            Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown Error")
        }
    }

    suspend fun getWinterFellCharactersDetails(id: Int): Resource<WinterFellCharacterDetailsResponse> {
        return try {
            val result = apiService.getCharacterById(id)
            Log.d("Winter fellRepository", "getWinterFellCharacters: $result")
            Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown Error")
        }
    }
}
