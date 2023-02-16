package com.example.meliapp.repository



import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.bank.BankItem
import com.example.meliapp.model.payment.method.PaymentMethodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentMethodRepository(private val dataSource: PaymentMethodDataSource): IPaymentMethodRepository {

    override suspend fun getPaymentMethods(): List<PaymentMethodItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentMethods()
        }
    }
    override suspend fun getPaymentBank(paymentMethod :String): List<BankItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentBank(paymentMethod = paymentMethod)
        }
    }
}
