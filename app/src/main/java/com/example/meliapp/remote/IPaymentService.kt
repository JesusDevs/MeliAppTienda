package com.example.meliapp.remote

import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.installments.InstallmentsResponseItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IPaymentService {
    @GET("payment_methods")
    suspend fun getPaymentMethods(@Query("public_key") public_key: String): Response<List<PaymentMethodItem>>
    @GET("payment_methods/card_issuers?")
    suspend fun getPaymentBanck(@Query("public_key") public_key: String,
                                @Query("payment_method_id") payment_method_id: String): Response<List<BankItem>>
    @GET("payment_methods/installments?")
    suspend fun getInstallments(@Query("public_key") public_key: String,
                                @Query("payment_method_id") payment_method_id: String,
                                @Query("amount") amount: Double,
                                @Query("issuer.id") bank_id: String): Response<List<InstallmentsResponseItem>>
}