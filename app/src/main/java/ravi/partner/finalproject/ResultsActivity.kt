package ravi.partner.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ravi.partner.finalproject.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    // ResultsActivity Properties
    private lateinit var binding: ActivityResultsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set the reference to our binding
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // connect the data up to the recycler view (unbundle the users)
        // because the data is in a bunch of bytes, and we know what it is the IDE wants us to double check
        @Suppress("UNCHECKED_CAST")
        val data = intent.getSerializableExtra(getString(R.string.user_data_key)) as ArrayList<Users>

        // get the reference to the action bar at the top of the activity
        // these are difficult times, we must suport the action bar
        supportActionBar?.title = "${data.size} Results"

        // these are 4 main steps to creating a recycler view
        // [x] declare the recycler view in activity layout
        // [ ] create hte custom xml layout for the recycler view
        // [ ] create a RecyclerView Adapter (create a view holder for view items, then connect data source)
        // [ ] attach the data to the recycler view
    }
}