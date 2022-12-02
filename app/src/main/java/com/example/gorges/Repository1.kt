package com.example.gorges

import android.content.SharedPreferences
import android.util.Log

object Repository1 {
    var savedPlaces = mutableListOf<Place>()
    private var numSaved = 0

    fun initSaved(sharedPrefs: SharedPreferences) {
        var num = sharedPrefs.getInt("NUMSAVED", 0)
        Log.d("REPOERROR num", "${num}")
        for (i in 0 until num) {
            var name = sharedPrefs.getString("PLACE${i}NAME", "error")
            var description = sharedPrefs.getString("PLACE${i}DESCRIPTION", "error")
            var activities = sharedPrefs.getString("PLACE${i}ACTIVITIES", "error")
            var category = sharedPrefs.getString("PLACE${i}CATEGORY", "error")
            //var ratings = sharedPrefs.getString("PLACE${i}RATINGS", "error")
            var id = sharedPrefs.getInt("PLACE${i}ID", 0)
            var position = sharedPrefs.getInt("PLACE${i}POSITION", 0)
            var emptyList: List<Int> = mutableListOf()

            val place = Place(id!!, name!!, description!!, category!!, activities!!)
            if (!savedPlaces.contains(place)) { savedPlaces.add(place) }
            Log.d("REPOERRORS prefs update", "YES ${num}${savedPlaces[0].name}")
        }
    }

    fun addPlace(place: Place, sharedPreferences: SharedPreferences, position: Int) {
        savedPlaces.add(place)
        numSaved++
        sharedPreferences.edit().putInt("NUMSAVED", numSaved)?.putInt("PLACE${numSaved - 1}ID", place.id)
            ?.putString("PLACE${numSaved - 1}NAME", place.name)
            ?.putString("PLACE${numSaved - 1}DESCRIPTION", place.description)
            ?.putString("PLACE${numSaved - 1}ACTIVITIES", place.activities)
            ?.putString("PLACE${numSaved - 1}CATEGORY", place.category)
            //?.putString("PLACE${numSaved - 1}RATINGS", place.ratings)
            ?.putInt("PLACE${numSaved - 1}POSITION", position)?.apply()
            Log.d("REPOERRORS did this add", "YES${numSaved}")
        Log.d("REPOERROR num", "${sharedPreferences.getInt("NUMSAVED", 0)}")
        Log.d("REPOERROR size", "${savedPlaces.size}")
        Log.d("REPOERRORS add?", "YES ${savedPlaces[0].name}${sharedPreferences.getString("PLACE0NAME", "ERROR")}")
    }

    fun removePlace(place: Place, sharedPreferences: SharedPreferences, position: Int) {
        var j = savedPlaces.indexOf(place)
        Log.d("REPOERRORS (did remove)", "YES")
        Log.d("REPOERRORS J", "${j}")

        if (j != -1) {
            val editor = sharedPreferences.edit()
            editor.remove("NUMSAVED")
            for (k in j until numSaved) {
                editor.remove("PLACE${k}NAME")
                    .remove("PLACE${k}DESCRIPTION")
                    .remove("PLACE${k}ACTIVITIES")
                    .remove("PLACE${k}CATEGORY")
                    //.remove("PLACE${k}RATINGS")
                    .remove("PLACE${k}ID")
                    .remove("PLACE${k}POSITION").apply()
            }

            savedPlaces.remove(place) // this way, the place won't be in there if you click on saved from within app
            numSaved -= 1 // helps us iterate through saved
            editor.putInt("NUMSAVED", numSaved)?.apply()
            Log.d("REPOERRORS (did remove)", "YES")
            Log.d("REPOERRORS remove size", "${numSaved}")


            for (k in j until numSaved) {
                editor.putString("PLACE${k}NAME", savedPlaces[k].name)
                    ?.putString("PLACE${k}DESCRIPTION", savedPlaces[k].description)
                    ?.putString("PLACE${k}ACTIVITIES", savedPlaces[k].activities)
                    ?.putString("PLACE${k}CATEGORY", savedPlaces[k].category)
                    //?.putString("PLACE${k}RATINGS", savedPlaces[k].ratings)
                    ?.putInt("PLACE${k}ID", savedPlaces[k].id)
                    ?.putInt("PLACE${k}POSITION", position)?.apply()
            }
        }
        //unsavedPlacesPositions.add(j) // must check this before loading shared preferences
    }
}