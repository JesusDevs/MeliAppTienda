package com.example.meliapp.core.status

import java.lang.Exception

sealed class UIState<out T> {
    class Loading<out T>: UIState<T>()
    data class Success<out T>(val data: T): UIState<T>()
    data class Failure(val exception: Exception): UIState<Nothing>()
}