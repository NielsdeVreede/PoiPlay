package com.minor.poiplay

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.minor.poiplay.Components.EventAttendant
import kotlinx.android.synthetic.main.event_overview_page.*

class EventOverviewPage : Fragment(R.layout.event_overview_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var attendantsData: Array<String> = arrayOf("Dary van Sleeuwen", "Niels de Vreede", "Jeroen van Alpen", "Rick Barten", "Luuk Josephs", "Koen Rode", "Peter van der Meer")

        join_event_button.onClick = {
            //TODO - Join user to event in backend
            println("join event")
        }

        join_event_button.setText("Ik kom naar dit event")

        //TODO load dynamic data from database for specific location
        event_title.text = "Groot partijtje "
        event_time_title.text = "12:30"
        event_location_title.text = "Voetbalveld, Voskuilen 2A"
        event_description.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dui felis, imperdiet eu urna ut, malesuada tempus ligula"
        event_attendant_number.text = "7"

        for(attendant in attendantsData){
            val attendant = EventAttendant(getContext(), null, attendant)
            event_attendants.addView(attendant)
        }
    }
}