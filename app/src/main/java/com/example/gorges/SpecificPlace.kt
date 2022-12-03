package com.example.gorges

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SpecificPlace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_place)

        val nameDetailText = findViewById<TextView>(R.id.nameDetailText)
        val descriptionDetailText = findViewById<TextView>(R.id.descriptionDetailText)
        val activitiesDetailText = findViewById<TextView>(R.id.activitiesDetailText)
        val backButton = findViewById<Button>(R.id.backToAllButton)

        nameDetailText.text = intent.extras?.getString("Name")
        descriptionDetailText.text = "Details: \n" + intent.extras?.getString("Description")
        activitiesDetailText.text = "Suggested activities: \n" + intent.extras?.getString("Activities")

        backButton.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            intentToMain.putExtra("Fragment", intent.extras?.getInt("Fragment"))
            startActivity(intentToMain)
        }
    }
}