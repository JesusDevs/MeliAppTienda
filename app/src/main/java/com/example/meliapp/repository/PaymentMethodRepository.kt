package com.example.meliapp.repository



import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.PaymentMethodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentMethodRepository(private val dataSource: PaymentMethodDataSource): IPaymentMethodRepository {

    override suspend fun getPaymentMethods(): List<PaymentMethodItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentMethods()
        }
    }
}
