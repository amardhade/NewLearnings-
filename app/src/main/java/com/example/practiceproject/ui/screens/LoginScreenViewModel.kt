package com.example.practiceproject.ui.screens

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceproject.core.data.ResponseState
import com.example.practiceproject.data.ApiError
import com.example.practiceproject.data.AppError
import com.example.practiceproject.data.NetworkError
import com.example.practiceproject.data.getErrorMsg
import com.example.practiceproject.domain.CountryRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val countryRepo: CountryRepo
) : ViewModel() {

//    private val emailState = savedStateHandle.getStateFlow("email", "")
//    private val passwordState = savedStateHandle.getStateFlow("password", "")

    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    private val _screenEvent = Channel<LoginScreenEvent>()
    val screenEvent = _screenEvent.receiveAsFlow()

    private val showToast: Boolean = false
    private val _toastState = MutableStateFlow(showToast)
    val toastState = _toastState.asStateFlow()


    init {
        fetchPopulation()
    }

    fun updateEmail(email: String) {
//        savedStateHandle["email"] = email
        _state.update { it.copy(email = email) }
    }

    fun updatePassword(pwd: String) {
//        savedStateHandle["password"] = pwd
        _state.update { it.copy(password = pwd) }
    }

    fun updateToastState() {
        _toastState.update { it.not() }
    }

    private fun updateErrorMsg(errorMsg: String) {
        _state.update {
            it.copy(error = errorMsg)
        }
    }

    fun doLogin() {
        updateErrorMsg("")
        val email = _state.value.email ?: ""
        if (!validateEmail(email)) {
            updateErrorMsg("Invalid Email")
            updateToastState()
            return
        }
        val pwd = _state.value.password ?: ""
        if (!validatePwd(pwd)) {
            updateErrorMsg("Invalid password")
            updateToastState()
            return
        }

        _screenEvent.trySend(LoginScreenEvent.NavigateToHome(email, pwd))
    }

    private fun fetchPopulation() {
        viewModelScope.launch {
            when(val response = countryRepo.fetchCountryPopulation()) {
                is ResponseState.Success ->{
                    Log.d("TAG", "Success: ${response.getSuccessData()}")
                }
                is ResponseState.Error -> {
                    updateErrorMsg(errorMsg = getErrorMsg(response.getError()))
                    updateToastState()
                    Log.d("TAG", "Error: ${response.getError()}")
                }

                ResponseState.Idle -> TODO()
                ResponseState.Loading -> {
                    Log.d("TAG", "Loading: ")
                }
            }
        }
    }

//    fun shouldShowToast(shouldShow: Boolean) = _toastState.update { shouldShow }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    private fun validatePwd(pwd: String): Boolean {
        if (pwd.isEmpty()) return false
        return pwd.length >= 4
    }


}

