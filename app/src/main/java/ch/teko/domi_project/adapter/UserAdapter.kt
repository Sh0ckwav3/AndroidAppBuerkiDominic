package ch.sik.teko.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.teko.domi_project.R
import ch.sik.teko.home.models.User
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val callback: AdapterCallback): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //A generic ordered collection of elements that supports adding and removing elements.
    private var data = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    fun setData(data: MutableList<User>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position >= 0) {
            val user = data[position]

            if (holder is UserViewHolder) {
                holder.bind(user, callback)
            }
        }

    }


    private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val transform = CropCircleTransformation()

        fun bind(user: User, callback: AdapterCallback) {
            //Load the image to the corresponding image view
            Picasso.get().load(user.picture.large)
                .transform(transform)
                .into(itemView.user_img)
            //Set the user name
            itemView.user_name.text = user.name.first
            //Bind the adapter click
            itemView.setOnClickListener {
                callback.onItemClicked(user, adapterPosition)
            }
        }
    }

    interface AdapterCallback {

        fun onItemClicked(user:User, position:Int)

    }
}