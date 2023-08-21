package com.milantejani.theonepractical.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Rain(
    @SerializedName("1h")
    val h: Double?
)