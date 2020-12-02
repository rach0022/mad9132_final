package ravi.partner.finalproject

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ravi.partner.finalproject.databinding.DataRowBinding

// our MainAdapter Interface
class MainAdapter(private val responseDataClass: ArrayList<Users>):
RecyclerView.Adapter<CustomViewHolderClass>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolderClass {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CustomViewHolderClass, position: Int) {
        TODO("Not yet implemented")
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

    // kotlin doesn't support static so we make a companion object that stores our values
    companion object{
        val titleKey = "ACTIONBAR_TITLE"
        val objectKey = "ITEM_DATA"
    }
}