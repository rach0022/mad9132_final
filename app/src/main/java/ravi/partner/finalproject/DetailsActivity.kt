package ravi.partner.finalproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.*
import ravi.partner.finalproject.databinding.ActivityDetailsBinding
import java.io.IOException

class DetailsActivity : AppCompatActivity() {

    // region properties of the DetailsActivity
    private lateinit var binding: ActivityDetailsBinding // create our binding

    // endregion

    // region Details Activities Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //init our view binding
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the title of the activity to the user login string from the bundle in the intent
        supportActionBar?.title = intent.getStringExtra(CustomViewHolderClass.titleKey)

        // now lets fetch our user data and then
        val data = intent.getSerializableExtra(CustomViewHolderClass.objectKey) as Users

        // set underline text on TextView control
        val text = data.html_url //"Underlined Text"
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, text.length, 0)
        binding.htmlURLTextView.text = content

        binding.htmlURLTextView.setOnClickListener{
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra(getString(R.string.url_key), data.html_url)
            this.startActivity(intent)
        }

        fetchJson(data.url)
    }

    //method to fetch the json from the currently selected user fron the intent
    private fun fetchJson(url: String){

        // We are using okhttp client here, not Retrofit2
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback { // can't execute from main thread!
            override fun onFailure(call: Call, e: IOException) {
                toast("Request Failed!")
            }

            override fun onResponse(call: Call, response: Response) {

                val body = response.body()?.string()

                val gson = GsonBuilder().create()
                val result = gson.fromJson(body, UserDetails::class.java)

                runOnUiThread {
                    Picasso.get().load(result.avatar_url).into(binding.avatarImageView)

                    binding.nameTextView.text =  getString(R.string.user_name, result?.name ?: "unknown")
                    binding.locationTextView.text =  getString(R.string.user_location, result?.location ?: "unknown")
                    binding.companyTextView.text =  getString(R.string.user_company, result?.company ?: "unknown")
                    binding.followersTextView.text =  getString(R.string.user_followers, result?.followers ?: "unknown")
                    binding.publicGistsTextView.text =  getString(R.string.user_public_gists, result?.public_gists ?: "0")
                    binding.publicReposTextView.text =  getString(R.string.user_public_repos, result?.public_repos ?: "0")
                    binding.lastUpdatedTextView.text =  getString(R.string.user_last_update, result?.updated_at?.replaceAfter(delimiter = 'T', replacement = "") ?: "Never")
                    binding.accountCreatedTextView.text =  getString(R.string.user_account_creation, result?.created_at?.replaceAfter(delimiter = 'T', replacement = "") ?: "Never")

                }
            }
        })
    }

    // extension method to toast the user based on this context
    // Extension function to show toast message
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    // endregion
}