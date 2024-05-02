package com.imprarce.android.local

sealed class ResponseRoom<out T>{

    data class Success<out T>(val result: T): ResponseRoom<T>()

    data class Failure(val exception: Exception): ResponseRoom<Nothing>()

    object Loading: ResponseRoom<Nothing>()
}