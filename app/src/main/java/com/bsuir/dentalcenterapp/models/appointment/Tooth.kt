package com.bsuir.dentalcenterapp.models.appointment

import com.google.gson.annotations.SerializedName

data class Tooth(
    @SerializedName("dentNumber")
    val number: Int,
    @SerializedName("isCured")
    val isRepeat: Boolean
)
