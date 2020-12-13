package ravi.anatolie.finalproject
// created by Ravi and Anatolie on December 11th, 2020
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ravi.anatolie.finalproject.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    // region ResultsActivity Properties
    private lateinit var binding: ActivityResultsBinding
    // endregion

    // region ResultsActivity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set the reference to our binding
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // connect the data up to the recycler view (unbundled the users)
        // because the data is in a bunch of bytes, and we know what it is the IDE wants us to double check
        @Suppress("UNCHECKED_CAST")
        val data = intent.getSerializableExtra(getString(R.string.user_data_key)) as ArrayList<Users>

        // get the reference to the action bar at the top of the activity
        // these are difficult times, we must support the action bar
        supportActionBar?.title = "${data.size} Results"

        // now to attach the data to the recycler view
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(this)

        // hook up the adapter to the recycler view using the data from our android intent (bundle)
        binding.recyclerViewMain.adapter = MainAdapter(data)



        // these are 4 main steps to creating a recycler view
        // [x] declare the recycler view in activity layout
        // [x] create the custom xml layout for the recycler view
        // [x] create a RecyclerView Adapter (create a view holder for view items, then connect data source)
        // [ ] attach the data to the recycler view
    }

    // endregion
}