package com.sean.oxford.adobeproject.persistence

sealed class CacheResult<out T> {

    data class Success<out T>(val data: T): CacheResult<T>()

    data class Error(val error: String?): CacheResult<Nothing>()

}