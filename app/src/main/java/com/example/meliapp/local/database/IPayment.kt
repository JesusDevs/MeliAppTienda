package com.example.meliapp.local.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.meliapp.local.entity.ItemProductEntity
import com.example.meliapp.model.payment.method.PaymentMethodItem

@Dao
interface IPayment {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemProduct(itemProductEntity: ItemProductEntity)

    @Delete
     suspend fun deleteItemProduct(itemProductEntity: ItemProductEntity)

    @Query("SELECT * FROM item_product")
    fun getAllItemProducts(): List<ItemProductEntity>
}