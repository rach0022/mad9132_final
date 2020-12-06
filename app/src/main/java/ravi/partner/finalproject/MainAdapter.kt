package ravi.partner.finalproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ravi.partner.finalproject.databinding.DataRowBinding

// our MainAdapter Interface
class MainAdapter(private val responseDataClass: ArrayList<Users>):
RecyclerView.Adapter<CustomViewHolderClass>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderClass {
        // A LayoutInflater reads an XML file in which we describe how we want a UI layout to be.
        // It then creates actual View objects for UI from that XML.
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.data_row, parent, false)
        return CustomViewHolderClass(cellForRow)
    }

    // set the values of the recycler view row based on the postion in the recycler view and our
    // custom view holder defined above
    override fun onBindViewHolder(holder: CustomViewHolderClass, position: Int) {
        holder.binding.loginTextView.text = TheApp.context.getString(R.string.user_login, responseDataClass[position].login)
        holder.binding.scoreTextView.text = TheApp.context.getString(R.string.user_score, (responseDataClass[position].score + 0.5).toInt().toString())
        holder.binding.idTextView.text = TheApp.context.getString(R.string.user_id, responseDataClass[position].id.toString())

        // third party image view technology that we use to load the image url from github and place it in the image view
        Picasso.get().load(responseDataClass[position].avatar_url).into(holder.binding.imageView)

        holder.login = responseDataClass[position].login
        // this is the entire user object that we will use later to view the user details in the next activity
        holder.user = responseDataClass[position]
    }


    // this is a one line function that will return an integer (kotlin shorthand)
    override fun getItemCount(): Int = responseDataClass.size

}

// constructor will take in the parameters of the view, a login string and a users
class CustomViewHolderClass(val view: View, var login: String = "",
                            var user: Users? = null) // a single Users object from ResponseDataClass/Users classes
    : RecyclerView.ViewHolder(view){
    // get a reference to the binding  of this view
    val binding = DataRowBinding.bind(view)

    // create an init method to add a click listener onto the view
    init {
        view.setOnClickListener{
            //create an android intent to pass along the user data to the next view
            val intent = Intent(view.context, DetailsActivity::class.java)
            // create the bundle that will actually hold the data being carried to the next view
            intent.putExtra(titleKey, login)
            intent.putExtra(objectKey, user) // place the full user in the bundle

            // lets start the next view now
            view.context.startActivity(intent)
        }
    }

    // kotlin doesn't support static so we make a companion object that stores our values
    companion object{
        val titleKey = "ACTIONBAR_TITLE"
        val objectKey = "ITEM_DATA"
    }
}