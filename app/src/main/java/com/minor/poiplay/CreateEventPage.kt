package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.create_event_page.*

class CreateEventPage : Fragment(R.layout.create_event_page) {
    private val args: CreateEventPageArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationInput.onTextChange = { text ->
            println(text)
        }

        descriptionInput.onTextChange = { text ->
            println(text)
        }

        descriptionInput.setMultiLine(true)
        descriptionInput.setHeight(400)

        create_event_button.setText("Event aanmaken")
        create_event_button.onClick = {
            //TODO - API call to save created event

            val action = CreateEventPageDirections.actionCreateEventPageToLocationSearcherPage()
            findNavController().navigate(action)
        }
    }
}