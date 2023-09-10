package com.example.winterfellchronicles.data.remote

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/v2/Characters")
    fun getCharacters(): Call<List<WinterFellResponse>>

    @GET("/api/v2/Characters/{id}")
    fun getCharacterById(): Call<WinterFellResponse>
}