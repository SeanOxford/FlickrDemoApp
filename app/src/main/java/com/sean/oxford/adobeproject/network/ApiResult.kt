package com.sean.oxford.adobeproject.network


sealed class ApiResult<out T> {

    data class Success<T>(val data: T) : ApiResult<T>()

    data class GenericError(val status: Int,
                            val message: String
    ):ApiResult<Nothing>()

}