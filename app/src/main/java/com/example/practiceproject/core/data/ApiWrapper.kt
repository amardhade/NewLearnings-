package com.example.practiceproject.core.data

import com.example.practiceproject.core.data.utilities.NetworkMonitor
import com.example.practiceproject.data.ApiError
import com.example.practiceproject.data.AppError
import com.example.practiceproject.data.NetworkError
import com.example.practiceproject.data.getKoinInstance
import kotlinx.coroutines.flow.first
import retrofit2.Response

suspend fun <T> apiWrapper(api: suspend () -> Response<T>): ResponseState<T> {
    var responseState: ResponseState<T> = ResponseState.Idle

    val networkMonitor: NetworkMonitor = getKoinInstance()
    // Internet not available
    if (networkMonitor.isConnected.first().not()) {
        return ResponseState.Error(
            appError = NetworkError.InternetError(
                msg = "No internet error...",
                msgCode = 0
            )
        )
    }

    try {
        val response = api()
        if (response.isSuccessful && response.body() != null) {
            responseState = ResponseState.Success(data = response.body()!!)
        } else {
            responseState = when (response.code()) {
                401 -> ResponseState.Error(
                    appError = ApiError.UnauthorizedUser(
                        msgCode = response.code(),
                        msg = response.message()
                    )
                )

                403 -> ResponseState.Error(
                    appError = ApiError.DeactivateUser(
                        msgCode = response.code(),
                        msg = response.message()
                    )
                )

                else -> ResponseState.Error(
                    appError = AppError.UnknownError(
                        msgCode = response.code(),
                        msg = response.message()
                    )
                )
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        responseState = when (e) {
            is IllegalStateException -> ResponseState.Error(
                appError = NetworkError.HttpError(
                    msgCode = 0,
                    msg = e.message ?: "Unknown Error"
                )
            )

            else -> ResponseState.Error(
                appError = AppError.UnknownError(
                    msgCode = 0,
                    msg = e.message ?: "Unknown Error"
                )
            )
        }

    }
    return responseState
}