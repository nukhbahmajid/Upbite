package hu.ait.mobilefinal.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import hu.ait.mobilefinal.HomeScreen

import hu.ait.mobilefinal.R
import hu.ait.mobilefinal.data.RestaurantItem
import hu.ait.mobilefinal.data.YelpSearchResult
import hu.ait.mobilefinal.network.YelpAPI
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.searchIcon.setOnClickListener {
            val retrofit = Retrofit.Builder().baseUrl(HomeScreen.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
            HomeScreen.yelpAPI = retrofit.create(YelpAPI::class.java)
            HomeScreen.yelpAPI.searchRestaurants("Bearer ${HomeScreen.API_KEY}", view.searchQuery.text.toString(), "California").enqueue(object :
                Callback<YelpSearchResult> {
                override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                    var body  = response.body()
                    if(body == null){
                        Toast.makeText((activity as HomeScreen), "Error: Not receiving the right response from Yelp API",
                            Toast.LENGTH_SHORT).show()
                    }

                    /*
                        START - just testing clearing out the list
                     */
                    HomeScreen.restaurants.clear()
                    /*
                        END - just testing clearing out the list
                     */

                    view.searchQuery.text.clear()

                    HomeScreen.restaurants.addAll(body!!.restaurants)
                    (activity as HomeScreen).makeCurrentFragment(HomeScreen.homeFragment)

                }

                override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                    Log.d("BODY_EMPTY", "the response is off - on failure")
                    Log.d("YELP", "onResponse $t")
                }

            })


            // implement what happens with search with query
            //(activity as HomeScreen).searchCallToYelpAPI((activity as HomeScreen).getAPIKey(),view.searchQuery.toString())
            //refreshRecyclerView()

            var adapter = GroupAdapter<GroupieViewHolder>()
            var listOfRestaurants = (activity as HomeScreen).getRestaurantsList()
            for(res in listOfRestaurants) {
                adapter.add(RestaurantItem(res))
            }
            adapter.notifyDataSetChanged()

            view.recyclerHome.adapter = adapter

        }


        var adapter = GroupAdapter<GroupieViewHolder>()
        var listOfRestaurants = (activity as HomeScreen).getRestaurantsList()
        for(res in listOfRestaurants) {
            adapter.add(RestaurantItem(res))
        }
        adapter.notifyDataSetChanged()

        view.recyclerHome.adapter = adapter

        return view
    }

    fun refreshRecyclerView() {

    }


}
