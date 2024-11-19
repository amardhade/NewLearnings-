package com.example.practiceproject.data

import com.example.practiceproject.core.data.ResponseState
import com.example.practiceproject.core.data.apiWrapper
import com.example.practiceproject.data.dtos.PopulationDto
import com.example.practiceproject.domain.CountryRepo

class CountryRepoImpl(private val apiService: ApiService): CountryRepo {

    override suspend fun fetchCountryPopulation(): ResponseState<PopulationDto> {
        return apiWrapper { apiService.getData("Nation", "Population") }
    }
}