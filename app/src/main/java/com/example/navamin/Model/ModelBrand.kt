package com.example.navamin.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ModelBrand (

    val model_id: String = "",
    val name_model: String = "",
    val brand_id: String = ""
): Parcelable