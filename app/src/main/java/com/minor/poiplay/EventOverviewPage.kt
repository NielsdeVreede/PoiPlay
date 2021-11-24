package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.event_overview_page.*

class EventOverviewPage : Fragment(R.layout.event_overview_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event_title.text = "Groot partijtje "
        event_time_title.text = "12:30"
        event_location_title.text = "Voetbalveld, Voskuilen 2A"
        event_description.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dui felis, imperdiet eu urna ut, malesuada tempus ligula"
        event_attendant_number.text = "12"
    }
}