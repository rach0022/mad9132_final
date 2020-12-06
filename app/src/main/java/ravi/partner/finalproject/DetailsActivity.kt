package ravi.partner.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ravi.partner.finalproject.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    // properties of the DetailsActivity
    private lateinit var binding: ActivityDetailsBinding // create our binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //init our view binding
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set the title of the activity to the user login string from the bundle in the intent
        supportActionBar?.title = intent.getStringExtra(CustomViewHolderClass.titleKey)
    }
}