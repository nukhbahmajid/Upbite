package hu.ait.mobilefinal.data

import com.google.gson.annotations.SerializedName

data class YelpRestaurants (
    val name : String,
    val rating : Double,
    val price : String,
    val categories : List<YelpCategory>,
    val location : YelpLocation,
    @SerializedName("review_count") val numReviews : Int,
    @SerializedName("distance") val distanceInMeters : Double,
    @SerializedName("image_url") val imageUrl : String

) {
   fun displayDistance() : String {
       val milesPerMeter = 0.000621371
       val distanceInMiles = "%.2fmi".format(distanceInMeters * milesPerMeter)
       return distanceInMiles
   }
}