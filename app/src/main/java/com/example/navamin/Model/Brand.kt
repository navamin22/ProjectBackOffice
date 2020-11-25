package com.example.navamin.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Brand (

    val brand_id: String = "",
    val brand_name: String = ""
): Parcelable