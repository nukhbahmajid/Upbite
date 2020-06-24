package hu.ait.mobilefinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import hu.ait.mobilefinal.data.*

import hu.ait.mobilefinal.fragments.*
import hu.ait.mobilefinal.network.YelpAPI
import kotlinx.android.synthetic.main.activity_home_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreen : AppCompatActivity() {

    companion object {
        lateinit var yelpAPI: YelpAPI
        val homeFragment = HomeFragment()
        val listsFragment = ListsFragment()
        val msgFragment = MessagesFragment()
        val userFragment = UserFragment()
        var OTHER_USER = UserItem(hu.ait.mobilefinal.data.User())
        var CURRENT_USER : User = User()
        val restaurants = mutableListOf<YelpRestaurants>()
        val BASE_URL = "https://api.yelp.com/v3/"
        val API_KEY = "Opsp-gQDV9oiou-pLhCO8NHCvY64QlbthmPUmcf1v7Smohc6iyFUZ15wvjHX7YabLe0u5cESHvvBOkxvcrY5IIUreD-MbzlIVwKOpMSefjL2bBDshLNeIipNlCW5XnYx"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        /*
        Connection set up with the yelp api
         */
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        yelpAPI = retrofit.create(YelpAPI::class.java)

        yelpAPI.searchRestaurants("Bearer $API_KEY","Coffee", "New York").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.d("YELP", "onResponse $response")
                var body  = response.body()
                if(body == null){
                    Toast.makeText(this@HomeScreen, "Error: Not receiving the right response from Yelp API",
                        Toast.LENGTH_SHORT).show()
                }

                restaurants.addAll(body!!.restaurants)

            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.d("YELP", "onResponse $t")
            }

        })

        makeCurrentFragment(homeFragment)


        navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> makeCurrentFragment(homeFragment)
                R.id.navLists -> makeCurrentFragment(listsFragment)
                R.id.navMsg -> makeCurrentFragment(msgFragment)
                R.id.navUser -> makeCurrentFragment(userFragment)
            }

            true
        }


    }

    /**
     * Call to the Yelp API when requested through the home fragment
     */
    fun searchCallToYelpAPI(searchTerm : String, location: String = "New York") {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        yelpAPI = retrofit.create(YelpAPI::class.java)
        yelpAPI.searchRestaurants("Bearer $API_KEY", searchTerm, location).enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.d("YELP", "onResponse $response")
                Log.d("API", "yelp API $yelpAPI")
                var body  = response.body()
                if(body == null){
                    Toast.makeText(this@HomeScreen, "Error: Not receiving the right response from Yelp API",
                        Toast.LENGTH_SHORT).show()
                }

                /*
                    START - just testing clearing out the list
                 */
                restaurants.clear()
                /*
                    END - just testing clearing out the list
                 */

                restaurants.addAll(body!!.restaurants)
                makeCurrentFragment(homeFragment)

            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.d("BODY_EMPTY", "the response is off - on failure")
                Log.d("YELP", "onResponse $t")
            }

        })

    }


    fun getAPIKey() : String {
        return API_KEY
    }



    /**
     * Function to call when changing fragments.
     */
    fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            if(fragment.javaClass == NewMessageFragment().javaClass || fragment.javaClass == MessagesFragment().javaClass ) {
                addToBackStack(null)

            }

            if(fragment.javaClass == ChatLogFragment().javaClass) {
                supportFragmentManager.popBackStack()
            }
            commit()


        }

    }

    fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }


    fun getRestaurantsList() : List<YelpRestaurants> {
        return restaurants
    }


    fun setOtherUserTo(userItem : UserItem) {
        OTHER_USER = userItem
    }

    fun getOtherUser() : UserItem {
        return OTHER_USER
    }

    fun setCurrentUser(user : User) {
        CURRENT_USER = user
    }

    fun getCurrentUser() : User {
        return CURRENT_USER
    }

    fun setSupportActionBarText(text : String) {
        supportActionBar?.title = text
    }





}

