package com.example.gorges

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavBar : BottomNavigationView = findViewById(R.id.bottomNavigationView)

        val sharedPrefs = getSharedPreferences("Saved", 0)
        Repository1.initSaved(sharedPrefs)

//                for (i in it.indices) {
//                    Log.d("NETWORKING CALL", it[i].name)
//                    if (it[i].category.equals("food"))
//                        myFoodPlaces.add(it[i])
//                    else if (it[i].category.equals("nature"))
//                        myNaturePlaces.add(it[i])
//                    else if (it[i].category.equals("tourism"))
//                        myTourismPlaces.add(it[i])
//                }

            Log.d("REPOERROR main num", "${sharedPrefs.getInt("NUMSAVED", 0)}")

            bottomNavBar.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_item -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.bottomNavFragmentContainer, HomeFragment.newInstance("", "")).commit()
                    }

                    R.id.saved_item -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.bottomNavFragmentContainer, SavedFragment.newInstance("", "")).commit()
                    }
                }
                true
            }

            //DO NETWORKING IN HERE


        }
    }
