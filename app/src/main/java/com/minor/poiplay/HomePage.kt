package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.home_page.*

class HomePage : Fragment(R.layout.home_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maps_picker_navigator.setOnClickListener {
            val action = HomePageDirections.actionHomePageToMapsPickerPage()
            findNavController().navigate(action)
        }

        new_video_navigator.setOnClickListener {
            val action = HomePageDirections.actionHomePageToMapsPickerPage()
            findNavController().navigate(action)
        }
    }
}