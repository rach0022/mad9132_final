package ravi.partner.finalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ravi.partner.finalproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // region Main Activity Properties
    val BASE_URL = "https://api.github.com/search/"
    var searchString = ""
    private lateinit var binding: ActivityMainBinding
    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        // set up the view binding, UI is now avaliable to us
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchButton.setOnClickListener{
            fetchData()
        }
    }

    private fun fetchData() {
        //create a retroFit Object, the object that preforms all of our fetching
        // GSON is a library that converts data into json and json into objects
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // create our reference to the RESTAPI object
        val restAPI = retrofit.create(RestAPI::class.java)

        // get the search from the user text input
        searchString = binding.searchUser.text.toString()

        // make the call to the RESTAPI (github) and get the user data
        val call = restAPI.getUserData(searchString)

        // make the call asyncrhnously and use a callback when the GET is done
        call.enqueue(object: Callback<ResponseDataClass>{
            override fun onResponse(call: Call<ResponseDataClass>, response: Response<ResponseDataClass>) {
                val responseBody = response.body()

                // now to transfer the data to our bundle to be sent off to the next activity
                val users = responseBody?.items
                val length = users?.size ?: 0

                // now to choose if we want to display data (if we get results)
                // no point in displaying data if we dont have any
                if (length > 0){
                    // the intent has our target Activity (ResultsActivity)
                    val intent = Intent(TheApp.context, ResultsActivity::class.java) // create the intent
                    intent.putExtra(getString(R.string.user_data_key), users) // add the data to the bundle
                    startActivity(intent) // start the next activity and send the bundle with our intent
                }
            }

            override fun onFailure(call: Call<ResponseDataClass>, t: Throwable) {
                toast(t.message.toString())
            }

        })
    }

    // Adding the extension method from RandomWebImageApp into the MainActivity
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}