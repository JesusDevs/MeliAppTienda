package com.example.meliapp.repository



import com.example.meliapp.datasource.PaymentMethodDataSource
import com.example.meliapp.model.payment.PaymentMethodItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PaymentMethodRepository(private val dataSource: PaymentMethodDataSource): IPaymentMethodRepository {

    //trae completo el listado de juegos de todas las plataformas desde fb no se usa por ahora
    override suspend fun getLatesGames(): List<PaymentMethodItem> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getPaymentMethods()
        }
    }
}
