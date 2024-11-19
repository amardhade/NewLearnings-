package com.example.practiceproject.data

import com.example.practiceproject.data.dtos.PopulationDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data")
    suspend fun getData(
        @Query("drilldowns") drilldowns: String,
        @Query("measures") measures: String
    ): Response<PopulationDto>
}