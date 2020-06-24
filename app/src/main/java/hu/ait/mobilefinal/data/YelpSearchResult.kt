package hu.ait.mobilefinal.data

import com.google.gson.annotations.SerializedName

data class YelpSearchResult (
    @SerializedName("total") val total : Int,
    @SerializedName("businesses") val restaurants : List<YelpRestaurants>
)