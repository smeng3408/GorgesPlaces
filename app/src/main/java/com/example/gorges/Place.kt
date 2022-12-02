package com.example.gorges

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Place(
    //var id: String, //figure this out later: will need to modify Custom Adapter, Tab Fragment, Repository!!
    @Json(name = "id") var id: Int,
    @Json(name = "name")var name: String,
    @Json(name = "description")var description: String,
    @Json(name = "category")var category: String,
    @Json(name = "activity")var activities: String,
) {
}