package com.example.meliapp.remote.interceptors

import android.util.Log
import com.example.meliapp.model.interceptor.ErrorService
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.SocketTimeoutException


class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        try {
            val response = chain.proceed(request)
            val bodyString = response.body!!.string()

            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()
        } catch (e: Exception) {
            Log.d("internet Error","${e.message}")
            var msg = ""
            var cause = ""
            var interceptorCode: Int = 0
            when (e) {

                is SocketTimeoutException -> {
                   //desconectar socket y volver a conectar
                    msg = e.message.toString()
                    cause = e.cause?.printStackTrace().toString()?:""
                    interceptorCode = 408
                    Log.d("internet Socket timeout error",msg)

                }else -> {
                    msg = e.message.toString()
                    cause = e.cause?.printStackTrace().toString()?:""
                    interceptorCode = 501
                    Log.d("internet Error distinto de SocketTimeoutException",msg)
                }
                // Add additional errors... //
            }
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(interceptorCode)
                .message(msg)
                .body(ErrorService("cause : $cause",interceptorCode.toString(),msg,interceptorCode,"","").toString().toResponseBody())
                .build()
        }
    }
}