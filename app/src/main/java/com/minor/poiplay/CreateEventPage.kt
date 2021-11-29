package com.minor.poiplay

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.create_event_page.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class CreateEventPage : Fragment(R.layout.create_event_page) {
    private val args: CreateEventPageArgs by navArgs()

    private val defaultUrl = SettingsFactory.getDefaultUrl()
    private lateinit var queue: RequestQueue;

    private var location: String = ""
    private var description: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queue = Volley.newRequestQueue(requireContext())

        
        locationInput.onTextChange = { text ->
            location = text
            println(text)
        }

        descriptionInput.onTextChange = { text ->
            description = text
            println(text)
        }

        descriptionInput.setMultiLine(true)
        descriptionInput.setHeight(400)

        create_event_button.setText("Event aanmaken")
        create_event_button.onClick = {
            createNewEvent()
        }
    }


    private fun createNewEvent(){
        val date = Date()
        date.hours = time_input_left.text.toString().toInt()
        date.minutes = time_input_right.text.toString().toInt()

        val params = HashMap<String,String>()
        params["poi_id"] = args.poiId.toString()
        params["name"] = description
        params["time"] = date.toString()
        val jsonObject = JSONObject(params as Map<*, *>)

        val req = JsonObjectRequest(
            Request.Method.POST,"$defaultUrl/event", jsonObject,
            { response ->
                val action = CreateEventPageDirections.actionCreateEventPageToMapsPickerPage()
                findNavController().navigate(action)
            },
            { error ->
                val action = CreateEventPageDirections.actionCreateEventPageToMapsPickerPage()
                findNavController().navigate(action)
            })
        queue.add(req)
    }
}