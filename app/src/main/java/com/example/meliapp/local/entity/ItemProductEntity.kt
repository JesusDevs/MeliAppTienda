package com.example.meliapp.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_product")
data class ItemProductEntity(
    @PrimaryKey val id: String,
    val title: String?,
    val price: Int?,
    val thumbnail: String?,
    val condition: String?,
    val description: String?,
    val quantity: Int?,
    val favorite: Boolean = false,
    val total : Int? = price?.times(quantity ?: 0)
)