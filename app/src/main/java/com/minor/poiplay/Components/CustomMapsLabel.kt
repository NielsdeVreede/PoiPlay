package com.minor.poiplay.Components

import android.content.Context
import android.view.View
import com.google.android.gms.maps.model.Marker
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.minor.poiplay.R
import kotlinx.android.synthetic.main.custom_maps_label.view.*

class CustomMapsLabel(context: Context) :
    InfoWindowAdapter {
    private val labelView: View =
        LinearLayout.inflate(context, R.layout.custom_maps_label, null)

    private fun rendowWindowText(marker: Marker) {
        var labelText = labelView.findViewById(R.id.label_text) as TextView
        labelText.text = marker.title
    }

    override fun getInfoWindow(marker: Marker): View {
        rendowWindowText(marker)
        return labelView
    }

    override fun getInfoContents(marker: Marker): View {
        rendowWindowText(marker)
        return labelView
    }
}