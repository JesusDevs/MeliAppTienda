package com.example.meliapp.remote

import com.example.meliapp.remote.interceptors.ErrorInterceptor
import com.example.meliapp.remote.interceptors.IPaymentService
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object{
        private const val BASE_URL = "https://api.mercadopago.com/v1"
                fun getRetrofitInstance(): IPaymentService {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY

                    val client = OkHttpOneShot
                        .client
                        .newBuilder()
                        .addInterceptor(ErrorInterceptor())
                        .addInterceptor(logger)
                        .build()


                    val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    return retrofit.create(IPaymentService::class.java)
                }
    }
}