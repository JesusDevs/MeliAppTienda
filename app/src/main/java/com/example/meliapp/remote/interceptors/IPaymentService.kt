package com.example.meliapp.remote.interceptors

import com.example.meliapp.model.payment.PaymentMethod
import com.example.meliapp.model.payment.PaymentMethodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPaymentService {
    @GET("{payment_methods}")
    suspend fun getPaymentMethods(@Path("payment_methods") payment_methods: String,
                                  @Query("public_key") public_key: String): Response<List<PaymentMethodItem>>
}