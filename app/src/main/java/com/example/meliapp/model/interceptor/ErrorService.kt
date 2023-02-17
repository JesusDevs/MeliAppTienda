package com.example.meliapp.model.interceptor

data class ErrorService(
    val type :String?,
    val code :String?,
    val reason :String?,
    val status :Int?,
    val path :String?,
    val timestamp :String?
)
