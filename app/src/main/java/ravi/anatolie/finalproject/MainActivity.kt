package ravi.anatolie.finalproject
// created by Ravi and Anatolie on December 11th, 2020
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ravi.anatolie.finalproject.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // region Main Activity Properties
    private val baseURL = "https://api.github.com/search/"
    private var searchString = ""
    private lateinit var binding: ActivityMainBinding

    // these properties are used for search
    private val minPage = 1
    private val maxPage = 100
    private val startPage = 30
    private val maxRepos = 1000
    private val maxFollowers = 10000

    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        // set up the view binding, UI is now available to us
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchButton.setOnClickListener{
            fetchData()
        }

        // set up the number pickers with the minimum values and max values
        binding.perPageNumberPicker.minValue = minPage
        binding.perPageNumberPicker.maxValue = maxPage
        binding.perPageNumberPicker.value = startPage

        // these are input filters that make sure we only get the input that we want in this app
        // using hte minimum value of zero and max value of amxRepos and maxFollowers
        binding.minReposEditText.filters  = arrayOf<InputFilter>(InputFilterMinMax(0, maxRepos))
        binding.minFollowersEditText.filters = arrayOf<InputFilter>(InputFilterMinMax(0, maxFollowers))

        // will set the text to zero when the app launches
        binding.minReposEditText.setText("0")
        binding.minFollowersEditText.setText("0")

        // all 3 overrides are required
        // these are the text change listeners that will run the code supplied when the following events happen
        binding.searchUser.addTextChangedListener(object : TextWatcher {
            // before text changed (text in its current state)
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            // when the text is changed we will enable the search button to be used
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.searchButton.isEnabled = binding.searchUser.text.toString().trim().isNotEmpty()
            }

            // after text is changed, if we have no text then we will disable the button
            override fun afterTextChanged(s: Editable) {
                binding.searchButton.isEnabled = s.isNotEmpty()
                binding.noResultsMessage.text = ""
            }
        })
    }

    // region Main Menu with About Section
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_about -> {
                val intent = Intent(TheApp.context, AboutActivity::class.java) // add intent
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // endregion

    // region FetchData function to preform our request
    private fun fetchData() {
        //create a retroFit Object, the object that preforms all of our fetching
        // GSON is a library that converts data into json and json into objects
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // create our reference to the RESTAPI object
        val restAPI = retrofit.create(RestApi::class.java)

        // these 4 lines of new code will add the minFollower and minRepos from the number pickers to
        // to the request and will set the fields to zero if there are no values
        if(TextUtils.isEmpty(binding.minFollowersEditText.text)){
            binding.minFollowersEditText.setText("0")
        }

        if(TextUtils.isEmpty(binding.minReposEditText.text)){
            binding.minReposEditText.setText("0")
        }

        //must use shared preferences to save these values
        val minNumberOfFollowers = binding.minFollowersEditText.text.toString().toInt()

        val minNumberOfRepos = binding.minReposEditText.text.toString().toInt()

        // get the search from the user text input and number input for the min repos and followers
        //searchString = binding.searchUser.text.toString()
        searchString = "${binding.searchUser.text} repos:>=$minNumberOfRepos followers:>=$minNumberOfFollowers"

        // make the call to the RESTAPI (github) and get the user data
        //val call = restAPI.getUserData(searchString)
        val call = restAPI.getUserData(searchString, binding.perPageNumberPicker.value)

        // make the call asynchronously and use a callback when the GET is done
        call.enqueue(object: Callback<ResponseDataClass>{
            override fun onResponse(call: Call<ResponseDataClass>, response: Response<ResponseDataClass>) {
                val responseBody = response.body()

                // now to transfer the data to our bundle to be sent off to the next activity
                val users = responseBody?.items
                val length = users?.size ?: 0

                // now to choose if we want to display data (if we get results)
                // no point in displaying data if we don't have any
                if (length > 0){
                    // the intent has our target Activity (ResultsActivity)
                    val intent = Intent(TheApp.context, ResultsActivity::class.java) // create the intent
                    intent.putExtra(getString(R.string.user_data_key), users) // add the data to the bundle
                    startActivity(intent) // start the next activity and send the bundle with our intent
                } else { // if we get no search results lets display a message to the user and disable the button
                    binding.noResultsMessage.text = getString(R.string.no_results, binding.searchUser.text)
                    binding.searchButton.isEnabled = false
                }
            }

            override fun onFailure(call: Call<ResponseDataClass>, t: Throwable) {
                toast(t.message.toString())
            }

        })
    }

    // endregion

    // Adding the extension method from RandomWebImageApp into the MainActivity
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}