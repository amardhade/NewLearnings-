package com.example.practiceproject.data.dtos

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("ID Nation")
    val nationId: String,
    @SerializedName("ID Year")
    val yearId: Int,
    @SerializedName("Nation")
    val nation: String,
    @SerializedName("Population")
    val population: Int,
    @SerializedName("Slug Nation")
    val slugNation: String,
    @SerializedName("Year")
    val year: String
)