package com.example.practiceproject.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practiceproject.ui.screens.LoginScreen
import com.example.practiceproject.ui.screens.LoginScreenViewModel
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var loginScreenViewModel: LoginScreenViewModel

    @Before
    fun setUp() {
        val savedState = SavedStateHandle(mapOf("email" to "a@b.com", "password" to "1234"))
        loginScreenViewModel = LoginScreenViewModel(savedStateHandle = savedState)
        composeTestRule.setContent {
            LoginScreen(
                loginScreenViewModel = loginScreenViewModel,
                navigateToHome = { _, _ ->}
            )
        }
    }

    @Test
    fun invalidEmailShowsError() {
        composeTestRule.onNodeWithTag("email_field").performTextInput("abc")
        composeTestRule.onNodeWithTag("login_btn").performClick()
        assert(loginScreenViewModel.state.value.error.equals("Invalid Email"))
    }

    @Test
    fun invalidPwdShowsError() {
        composeTestRule.onNodeWithTag("email_field").performTextInput("abc")
        composeTestRule.onNodeWithTag("pwd_field").performTextInput("123")
        composeTestRule.onNodeWithTag("login_btn").performClick()
        assert(loginScreenViewModel.state.value.error.equals("Invalid password"))
    }
}