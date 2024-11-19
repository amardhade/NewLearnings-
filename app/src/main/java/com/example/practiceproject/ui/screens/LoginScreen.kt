package com.example.practiceproject.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel,
    navigateToHome: (email: String, pwd: String) -> Unit
) {

    val context = LocalContext.current

    val uiState = loginScreenViewModel.state.collectAsState()

    loginScreenViewModel.screenEvent.collectAsState(initial = null).value?.let { screenEvent ->
        when (screenEvent) {
            is LoginScreenEvent.NavigateToHome -> {
                navigateToHome(screenEvent.email, screenEvent.pwd)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = uiState.value.email ?: "",
            onValueChange = { it ->
                loginScreenViewModel.updateEmail(it)
            }, modifier = Modifier
                .fillMaxWidth()
                .testTag("email_field")
        )

        Spacer(
            modifier = Modifier
                .size(10.dp)
                .fillMaxWidth()
        )

        TextField(
            value = uiState.value.password ?: "", onValueChange = {
                loginScreenViewModel.updatePassword(it)
            }, modifier = Modifier
                .fillMaxWidth()
                .testTag("pwd_field")
        )

        Spacer(
            modifier = Modifier
                .size(40.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = { loginScreenViewModel.doLogin() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .testTag("login_btn")
        ) {
            Text(text = "Login")
        }
    }

    LaunchedEffect(key1 = loginScreenViewModel.toastState.collectAsState().value) {
        if (loginScreenViewModel.toastState.value) {
            Toast.makeText(context, uiState.value.error, Toast.LENGTH_SHORT).show()
            loginScreenViewModel.updateToastState()
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen()
//}