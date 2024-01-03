package com.example.winterfellchronicles.data.repository

import android.util.Log
import com.example.winterfellchronicles.data.remote.ApiService
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

    suspend fun getWinterfellCharactersDetails(id: Int): Resource<List<WinterFellResponse>> {
        return try {
            val result = apiService.getCharacterById(id)
            Log.d("WinterfellRepository", "getWinterFellCharactyers: $result")
            Resource.Success(result)
        } catch (e: Exception) {
            return Resource.Error(message = e.message ?: "Unknown Error")
        }
    }
}
