package ch.teko.domi_project.networking

import ch.teko.domi_project.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RestApi {

    //How long is the time out for the ok http client
    private const val TIMEOUT_IN_SEC = 3

    object Client {

        private var instance: Api? = null
        private var url: String = "https://randomuser.me/"

        fun build(endpoint: String = url) {
            val retrofit = buildRetrofit(endpoint)

            instance = retrofit.create(Api::class.java)
        }

        @Synchronized
        fun getInstance(): Api {
            if (instance == null) {
                throw IllegalStateException("api is not initialized")
            }
            return instance!!
        }

    }


    private fun buildRetrofit(endpoint: String): Retrofit {
        //Create a ok http instance builder for configuration
        val builder = OkHttpClient.Builder()
            .readTimeout(TIMEOUT_IN_SEC.toLong(), TimeUnit.SECONDS)

        //If we are in debug mode we wan't so see the actual web requests.
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }

        //This is the ok http instance
        val client = builder.build()

        //Build the Retrofit instance
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

}