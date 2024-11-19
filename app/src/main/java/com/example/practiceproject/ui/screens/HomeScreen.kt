package com.example.practiceproject.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    email: String, pwd: String
) {

    Text(text = "Email $email and password is $pwd" )

}