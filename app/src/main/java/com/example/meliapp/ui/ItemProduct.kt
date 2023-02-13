package com.example.meliapp.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemProduct(
    val id: String? = null,
    val title: String? = null,
    val price: Int? = null,
    val thumbnail: String? = null,
    val condition: String? = null
    ) : Parcelable