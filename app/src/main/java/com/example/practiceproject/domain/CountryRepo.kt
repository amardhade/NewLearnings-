package com.example.practiceproject.domain

import com.example.practiceproject.core.data.ResponseState
import com.example.practiceproject.data.dtos.PopulationDto

interface CountryRepo {

    suspend fun fetchCountryPopulation(): ResponseState<PopulationDto>
}