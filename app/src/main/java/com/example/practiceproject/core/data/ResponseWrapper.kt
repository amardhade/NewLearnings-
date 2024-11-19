package com.example.practiceproject.core.data

import com.google.gson.annotations.SerializedName

open class ResponseWrapper<T> {
    var responseData: T? = null
    var error: Error? = null
    var errorCode: Int? = null
}