package hu.ait.mobilefinal.network

import hu.ait.mobilefinal.data.YelpSearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpAPI {

    @GET("businesses/search")
    fun searchRestaurants(@Header("Authorization") auth : String, @Query("term") searchTerm : String,
                          @Query("location") location : String) : Call<YelpSearchResult>
}