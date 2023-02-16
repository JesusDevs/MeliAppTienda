package com.example.meliapp.datasource

import android.util.Log
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethod
import com.example.meliapp.model.payment.method.PaymentMethodItem
import com.example.meliapp.remote.RetrofitBuilder


class PaymentMethodDataSource {
    private var listPaymentMethod: List<PaymentMethodItem>? = null
        private var listPaymentBank: List<BankItem>? = null
    suspend fun getPaymentMethods() :List<PaymentMethodItem> {
        try { val response = RetrofitBuilder.getRetrofitInstance()
                .getPaymentMethods("444a9ef5-8a6b-429f-abdf-587639155d88")
            if (response.isSuccessful) {
                listPaymentMethod = response.body()!!
            } else {
                Log.d("PaymentMethodDataSource", "Error: ${response.errorBody()}")
            }
        }catch (e: Exception){
            Log.d("PaymentMethodDataSource", "Error: ${e.message}")
        }
        return listPaymentMethod ?: emptyList()
    }

    suspend fun getPaymentBank(paymentMethod:String) :List<BankItem> {
        try { val response = RetrofitBuilder.getRetrofitInstance()
            .getPaymentBanck("444a9ef5-8a6b-429f-abdf-587639155d88", paymentMethod)
            if (response.isSuccessful) {
                listPaymentBank = response.body()!!
            } else {
                Log.d("PaymentMethodDataSource", "Error: ${response.errorBody()}")
            }
        }catch (e: Exception){
            Log.d("PaymentMethodDataSource", "Error: ${e.message}")
        }
        return listPaymentBank ?: emptyList()
    }
}