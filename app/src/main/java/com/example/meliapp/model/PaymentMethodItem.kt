package com.example.meliapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PaymentMethodItem(
    @SerializedName("accreditation_time")
    val accreditationTime: Int? = null,
    @SerializedName("additional_info_needed")
    val additionalInfoNeeded: List<String?>? = null,
    @SerializedName("deferred_capture")
    val deferredCapture: String? = null,
    @SerializedName("financial_institutions")
    val financialInstitutions:@RawValue List<Any?>? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("max_allowed_amount")
    val maxAllowedAmount: Int? = null,
    @SerializedName("min_allowed_amount")
    val minAllowedAmount: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("payment_type_id")
    val paymentTypeId: String? = null,
    @SerializedName("processing_modes")
    val processingModes: List<String?>? = null,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String? = null,
    @SerializedName("settings")
    val settings:@RawValue List<Setting>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null
) : Parcelable