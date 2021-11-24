package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.location_searcher_page.*

class LocationSearcherPage : Fragment(R.layout.location_searcher_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event_overview_navigator.setOnClickListener {
            val action = LocationSearcherPageDirections.actionLocationSearcherPageToEventOverviewPage()
            findNavController().navigate(action)
        }

        event_creater_navigator.setOnClickListener {
            val action = LocationSearcherPageDirections.actionLocationSearcherPageToCreateEventPage()
            findNavController().navigate(action)
        }
    }
}