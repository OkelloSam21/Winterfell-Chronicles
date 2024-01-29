package com.example.winterfellchronicles.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/v2/Characters")
    suspend fun getCharacters(): List<WinterFellResponse>

    @GET("/api/v2/Characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int,
    ): WinterFellCharacterDetailsResponse
}
