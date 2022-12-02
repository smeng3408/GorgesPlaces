package com.example.gorges

import android.util.Log
import com.squareup.moshi.Moshi
import okhttp3.*
import java.io.IOException

private val client = OkHttpClient()

fun getPlaces(callback : (List<Place>) -> Unit) {
    Log.d("NETWORKING UTILS", "STARTED?")
    val request = Request.Builder()
        // URL given from API/Backend (make sure the URL + path
        // is corect for the given get request
        .url("http://35.199.9.240/api/places/simple/")
        // can also add headers with .addHeader e.g .addHeader("Authorization", "Bearer ${TOKEN}")
        .get()
        .build()


    Log.d("NETWORKING UTILS", "built request?")
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: java.io.IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: okhttp3.Response) {
            Log.d("NETWORKING UTILS", "inside on response?")
            if (!response.isSuccessful) {
                // Can also respond with some UI saying check internet, try again, network request failed,
                // etc. as opposed to exception (which will crash your app unless caught)!
                throw IOException("Unexpected code $response")
            } else {
                // Can get access to the body with response.body(). Can then use
                // Moshi techniques to convert said body to a Kotlin object if applicable
                // or you can just parse the body directly!
                Log.d("NETWORKING UTILS", "about to get data?")
                val body = response.body

//                val type = Types.newParameterizedType(
//                    List::class.java, //List:class.java must come first!
//                    Place::class.java
//                )

                val moshi: Moshi = Moshi.Builder().build()
                val adapter = moshi.adapter<Response>(Response::class.java)

                val places: Response = adapter.fromJson(body!!.source())!!
                Log.d("NETWORKING UTILS", places.toString())

                callback(places.places)
            }
        }
    })}