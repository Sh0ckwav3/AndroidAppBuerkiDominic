package ch.teko.domi_project.networking


import ch.sik.teko.home.models.UserApiRequest
import retrofit2.Call
import retrofit2.http.GET


interface Api {

    //Get random user from api
    @GET("api/")
    fun fetchSingleRandomUser(): Call<UserApiRequest>

}