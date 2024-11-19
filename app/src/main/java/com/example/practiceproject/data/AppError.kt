package com.example.practiceproject.data

sealed class AppError {

    data class UnknownError(val msgCode: Int, val msg: String): AppError()
}

sealed class ApiError: AppError() {

    data class SessionExpired(val msgCode: Int, val msg: String): ApiError()

    data class DeactivateUser(val msgCode: Int, val msg: String): ApiError()

    data class UnauthorizedUser(val msgCode: Int, val msg: String): ApiError()

}

sealed class NetworkError: ApiError() {

    data class HttpError(val msgCode: Int, val msg: String): NetworkError()

    data class TimeoutError(val msgCode: Int, val msg: String): NetworkError()

    data class InternetError(val msgCode: Int, val msg: String): NetworkError()

}

fun getErrorMsg(error: AppError): String {
    return when(error) {
        is AppError.UnknownError -> error.msg
        is ApiError.DeactivateUser -> error.msg
        is NetworkError.HttpError-> error.msg
        is NetworkError.InternetError -> error.msg
        is NetworkError.TimeoutError -> error.msg
        is ApiError.SessionExpired -> error.msg
        is ApiError.UnauthorizedUser -> error.msg
    }
}