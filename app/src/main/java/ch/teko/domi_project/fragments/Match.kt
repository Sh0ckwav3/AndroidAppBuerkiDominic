package ch.teko.domi_project.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ch.sik.teko.home.models.UserApiRequest
import ch.teko.domi_project.R
import ch.teko.domi_project.networking.RestApi
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropSquareTransformation
import kotlinx.android.synthetic.main.fragment_match.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.OutputStreamWriter


class Match : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRandomUser()
        }


    fun getRandomUser(){
        //Request a random user from the backend
        RestApi.Client.getInstance().fetchSingleRandomUser()
            .enqueue(object: Callback<UserApiRequest> {

                override fun onFailure(call: Call<UserApiRequest>, t: Throwable) {
                    //add an error toast
                    Toast.makeText(getActivity(), "An Error Occurred! Pls refresh Page!",
                        Toast.LENGTH_LONG).show()
                }


                override fun onResponse(call: Call<UserApiRequest>, response: Response<UserApiRequest>) {
                    if (response.isSuccessful) {
                        //We should have our data ready
                        response.body()?.users?.get(0)?.apply {
                            //Lets do some work when we have the data ready to use

                            //Image
                            Picasso.get().load(this.picture.large)
                                .transform(CropSquareTransformation())
                                .into(user_img)

                            //Name
                            match_name.text = "${this.name.first} ${this.name.last}"

                            //Age
                            match_age.text = "${this.registered.age}"

                            //City
                            match_city.text = this.location.city

                            //Username
                            match_nickname.text = this.login.username

                            //set onklicklistener for button clicks
                            like.setOnClickListener{
                                val jsonUser = Gson().toJson(this)
                                val outputStreamWriter =
                                    OutputStreamWriter(context?.openFileOutput("config.txt", Context.MODE_APPEND))
                                outputStreamWriter.write(jsonUser+"\n")
                                outputStreamWriter.close()

                                //get new random user after decision
                                getRandomUser()
                            }


                            dislike.setOnClickListener{
                                //get new random user after decision
                                getRandomUser()
                            }

                        }


                    } else {
                        //add an error toast
                        Toast.makeText(getActivity(), "Error while fetching and displaying Data!",
                            Toast.LENGTH_LONG).show()
                    }
                }
            })
    }



}
