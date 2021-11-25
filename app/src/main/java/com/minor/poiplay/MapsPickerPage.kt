package com.minor.poiplay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.maps_page.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MapsPickerPage : Fragment(R.layout.maps_page) {
    private val markerList: MutableMap<String, PoiEntity> = mutableMapOf()
    private val defaultUrl = "http://192.168.178.63:3000";

    private val callback = OnMapReadyCallback { googleMap ->
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET, "$defaultUrl/poi",
            { response ->
                //Convert json to list and add markers to map
                val listOfPOIs = Json.decodeFromString<List<PoiEntity>>(response)
                addMarkersToMap(listOfPOIs, googleMap)

                //Center around Eindhoven
                centerMapAroundLatLng(LatLng(51.441642, 5.4697225), googleMap)
            },
            {  error ->
                println(error)
            })
        queue.add(stringRequest)


        googleMap.setOnMarkerClickListener { marker ->
            if (popUpView.visibility == View.INVISIBLE){
                popUpView.visibility = View.VISIBLE
            }
            titleText.text = marker.title

            val customId = markerList[marker.id]?.id

            val req = StringRequest(
                Request.Method.GET, "$defaultUrl/attendance/$customId",
                { response ->
                    attendanceText.text = response

                },
                {  error ->
                    attendanceText.text = "ERROR"
                    println(error)
                })
            queue.add(req)

            false
        }
    }

    private fun centerMapAroundLatLng(centerPoint: LatLng, mMap: GoogleMap){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centerPoint))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(centerPoint, 10.0f)
        )
    }

    private fun addMarkersToMap(listOfPOIs: List<PoiEntity>, mMap: GoogleMap) {
        listOfPOIs.forEach {
            val internalId = mMap.addMarker(MarkerOptions()
                .position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                .title(it.name)).id
            markerList[internalId] = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


        create_new_event.setOnClickListener {
            val lat = 51.441642f //hardcoded for local testing
            val long = 5.4697225f //hardcoded for local testing
            findNavController().navigate(
                MapsPickerPageDirections.actionMapsPickerPageToCreateEventPage(
                    1.0f,
                    2.0f
                )
            )
        }
    }
}