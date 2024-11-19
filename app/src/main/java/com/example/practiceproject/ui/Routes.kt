package com.example.practiceproject.ui

import kotlinx.serialization.Serializable

@Serializable data object LoginRoute

@Serializable data class HomeRoute(
    val email: String,
    val pwd: String
)