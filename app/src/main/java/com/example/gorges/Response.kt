package com.example.gorges
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response (
    var places: List<Place>
        ){
}