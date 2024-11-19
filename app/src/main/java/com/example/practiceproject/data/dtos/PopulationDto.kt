package com.example.practiceproject.data.dtos

import com.example.practiceproject.core.data.ResponseWrapper
import com.google.gson.annotations.SerializedName

data class PopulationDto(
    @SerializedName("data")
    var countries: List<CountryDto>,
    @SerializedName("source")
    var sourceDto: List<SourceDto>
): ResponseWrapper<PopulationDto>()