package com.example.gorges

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabFragments.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragments : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryNum: Int? = 0
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryNum = it.getInt("CategoryNum")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tab_fragments, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.tabRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val intentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { results ->
            var isSaved = results.data?.extras?.getBoolean("isSaved")
            var position = results.data?.extras?.getInt("Position")
        }

        val sharedPrefs = requireActivity().getSharedPreferences("Saved", 0)
        var myFoodPlaces = mutableListOf<Place>()
        var myNaturePlaces = mutableListOf<Place>()
        var myTourismPlaces = mutableListOf<Place>()
        var foodAdapter = CustomAdapter(myFoodPlaces, requireActivity(), 0, intentLauncher, sharedPrefs)
        var natureAdapter = CustomAdapter(myNaturePlaces, requireActivity(), 0, intentLauncher, sharedPrefs)

        Log.d("NETWORKING CALL", "before")
        getPlaces {
            for (i in it.indices) {
                if (it[i].category.equals("Food"))
                    myFoodPlaces.add(it[i])
                else if (it[i].category.equals("Nature"))
                    myNaturePlaces.add(it[i])
                else
                    myTourismPlaces.add(it[i])
            }

            activity?.runOnUiThread {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        when (categoryNum) {
//            -1 -> {
//                recyclerView.adapter = CustomAdapter(myPlaces, requireActivity(), 0, intentLauncher, sharedPrefs)
//            }
            0 -> {
                //recyclerView.adapter = CustomAdapter(myFoodPlaces, requireActivity(), 0, intentLauncher, sharedPrefs)
                recyclerView.swapAdapter(foodAdapter, false)
            }
            1 -> {
                //recyclerView.adapter = CustomAdapter(myNaturePlaces, requireActivity(), 0, intentLauncher, sharedPrefs)
                //recyclerView.adapter?.notifyDataSetChanged()
                recyclerView.swapAdapter(natureAdapter, false)
            }
            2 -> {
                recyclerView.adapter = CustomAdapter(myTourismPlaces, requireActivity(), 0, intentLauncher, sharedPrefs)
                //recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param categoryNum Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabFragments.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(categoryNum: Int, param2: String) =
            TabFragments().apply {
                arguments = Bundle().apply {
                    putInt("CategoryNum", categoryNum)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}