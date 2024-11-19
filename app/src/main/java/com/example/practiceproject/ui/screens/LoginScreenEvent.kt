package com.example.practiceproject.ui.screens

sealed class LoginScreenEvent {

    data class NavigateToHome(val email: String, val pwd: String): LoginScreenEvent()
}