package com.example.navamin.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Stock (

    val id: String = "",
    val brand: String = "",
    val model: String = "",
    val model_id: String = "",
    var quantity: String = "",
    var quantity_enable: String = ""
): Parcelable


