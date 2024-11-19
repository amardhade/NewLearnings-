package com.example.practiceproject.core.data

import com.example.practiceproject.data.AppError

sealed class ResponseState<out T> {

    data class Success<out T>(val data: T) : ResponseState<T>()

    data class Error(val appError: AppError) : ResponseState<Nothing>()

    data object Idle : ResponseState<Nothing>()

    data object Loading : ResponseState<Nothing>()

    fun isError() = this as Error

    fun inSuccess() = this as Success

    fun getError() = (this as Error).appError

    fun getSuccessData() = (this as Success).data

}