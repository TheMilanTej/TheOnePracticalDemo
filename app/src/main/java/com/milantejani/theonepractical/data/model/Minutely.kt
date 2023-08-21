package com.milantejani.theonepractical.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Minutely(
    @SerializedName("dt")
    val dt: Int?,
    @SerializedName("precipitation")
    val precipitation: Int?
)