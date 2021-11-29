package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.minor.poiplay.Components.CustomMapsLabel
import com.minor.poiplay.Components.EventAttendant
import kotlinx.android.synthetic.main.event_overview_page.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EventOverviewPage : Fragment(R.layout.event_overview_page) {
    private val args: EventOverviewPageArgs by navArgs()

    private lateinit var poi: PoiEntity;
    private lateinit var event: EventEntity;
    private val defaultUrl = SettingsFactory.getDefaultUrl()
    private lateinit var queue: RequestQueue

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue = Volley.newRequestQueue(requireContext())

        val attendantsData: Array<String> = arrayOf("Dary van Sleeuwen", "Niels de Vreede", "Jeroen van Alpen", "Rick Barten", "Luuk Josephs", "Koen Rode", "Peter van der Meer")

        join_event_button.onClick = {
            //TODO - Join user to event in backend. Use args.eventID to join user
            val action = EventOverviewPageDirections.actionEventOverviewPageToMapsPickerPage()
            findNavController().navigate(action)
        }

        join_event_button.setText("Ik kom naar dit event")

        //TODO load dynamic data from database for specific location
        val stringRequest = StringRequest(
            Request.Method.GET, "$defaultUrl/poi",
            { response ->
                poi = Json.decodeFromString(response)
            },
            {  error ->
                println(error)
            })
        queue.add(stringRequest)




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