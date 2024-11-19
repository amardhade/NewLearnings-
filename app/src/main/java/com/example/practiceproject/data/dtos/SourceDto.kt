package com.example.practiceproject.data.dtos

data class SourceDto(
    val measures: List<String>,
    val name: String,
    val substitutions: List<Any>
)