package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.create_event_page.*

class CreateEventPage : Fragment(R.layout.create_event_page) {
    private val args: CreateEventPageArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationInput.setOnTextChange { text ->
            println(text)
        }
    }
}