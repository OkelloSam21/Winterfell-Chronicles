package com.example.winterfellchronicles.di

import com.example.winterfellchronicles.data.remote.ApiService
import com.example.winterfellchronicles.data.repository.WinterfellRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponenet::class)
object AppModule{
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://thronesapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesWinterfellRepository(apiService: ApiService): WinterfellRepository{
        return WinterfellRepository(apiService)
    }
}