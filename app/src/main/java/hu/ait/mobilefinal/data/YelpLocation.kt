package hu.ait.mobilefinal.data

import com.google.gson.annotations.SerializedName

data class YelpLocation(
    @SerializedName("city") val city : String,
    @SerializedName("state") val state  : String,
    @SerializedName("address1") val address : String
)