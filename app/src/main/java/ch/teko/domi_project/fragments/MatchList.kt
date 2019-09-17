package ch.teko.domi_project.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ch.sik.teko.home.adapter.UserAdapter
import ch.sik.teko.home.models.User
import ch.teko.domi_project.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_matchlist.*


class MatchList : Fragment(), UserAdapter.AdapterCallback {

     private val adapter = UserAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matchlist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set the adapter
        match_list.layoutManager = LinearLayoutManager(view.context)
        match_list.adapter = adapter

        val gson = Gson()
        val allLikedUsers = context?.getFileStreamPath("config.txt")?.bufferedReader()
            ?.readLines()?.map { gson.fromJson(it, User::class.java) }?.toMutableList()

        allLikedUsers?.let { adapter.setData(it) }
    }

    /**
     * Callback for the adapter
     */
    override fun onItemClicked(user: User, position: Int) {

    }
}
