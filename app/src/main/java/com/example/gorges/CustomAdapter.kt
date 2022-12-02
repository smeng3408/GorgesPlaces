package com.example.gorges

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: List<Place>, private val current: Context, private val frag: Int,
                    private val intentLauncher: ActivityResultLauncher<Intent>, private var sharedPrefs: SharedPreferences) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val placeNameText = view.findViewById<TextView>(R.id.placeNameText)
            val placeDescriptionText = view.findViewById<TextView>(R.id.placeDescriptionText)
            val cardView : CardView = view.findViewById(R.id.cardView)
            val savedButton : Button = view.findViewById(R.id.saveButton)
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.placeNameText.text = dataSet[position].name
        var actualDescription = dataSet[position].description
        if (actualDescription.length > 150) {
            holder.placeDescriptionText.text = actualDescription.substring(0, 150) + "..."
        } else { holder.placeDescriptionText.text = dataSet[position].description }
        val category = dataSet[position].category

        if (Repository1.savedPlaces.contains(dataSet[position])) {
            holder.savedButton.setBackgroundColor(Color.GRAY)
            holder.savedButton.text = "SAVED"
        } else {
            holder.savedButton.setBackgroundColor(Color.parseColor("#DB1919"))
            holder.savedButton.text = "SAVE"
        }

        holder.savedButton.setOnClickListener {
            if (holder.savedButton.text.equals("SAVE")) {
                Repository1.addPlace(dataSet[position], sharedPrefs, position)
                holder.savedButton.text = "SAVED"
                holder.savedButton.setBackgroundColor(Color.GRAY)
                Log.d("REPOERRORS adapter save", "yes")
                notifyItemChanged(position)
            } else if (holder.savedButton.text.equals("SAVED")) {
                Repository1.removePlace(dataSet[position], sharedPrefs, position)
                holder.savedButton.text = "SAVE"
                holder.savedButton.setBackgroundColor(Color.parseColor("#DB1919"))
                Log.d("REPOERRORS unsave", "yes")
                if (frag == 1)
                    notifyItemRemoved(position)
            }
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(current, SpecificPlace::class.java)
            intent.putExtra("ID", dataSet[position].id)
            intent.putExtra("Name", dataSet[position].name)
            intent.putExtra("Description", dataSet[position].description)
            intent.putExtra("Activities", dataSet[position].activities)
            intent.putExtra("Category", category)
            intent.putExtra("Position", position)
            current.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}