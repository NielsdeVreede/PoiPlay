package com.minor.poiplay

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.minor.poiplay.databinding.ActivityMapsBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import android.widget.LinearLayout.LayoutParams as LayoutParams1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var popUpView: View
    private lateinit var titleText: TextView
    private lateinit var attendanceText: TextView
    private lateinit var mapLayout: LinearLayout


    private val markerList: MutableMap<String, PoiEntity> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        /*Initialize frontend components*/
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        popUpView = findViewById(R.id.popUpView)
        popUpView.visibility = View.INVISIBLE
        titleText = findViewById(R.id.titleText)
        attendanceText = findViewById(R.id.attendanceText)
        mapLayout = findViewById(R.id.map_layout)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val defaultUrl = "http://145.93.113.32:3000";
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, "$defaultUrl/poi",
            { response ->

                //Convert json to list and add markers to map
                val listOfPOIs = Json.decodeFromString<List<PoiEntity>>(response)
                addMarkersToMap(listOfPOIs)

                //Center around Eindhoven
                centerMapAroundLatLng(LatLng(51.441642, 5.4697225))
            },
            {  error ->
                println(error)
            })
        queue.add(stringRequest)


        mMap.setOnMarkerClickListener { marker ->
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


    private fun centerMapAroundLatLng(centerPoint: LatLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centerPoint))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(centerPoint, 10.0f)
        )
    }

    private fun addMarkersToMap(listOfPOIs: List<PoiEntity>) {
        listOfPOIs.forEach {
            val internalId = mMap.addMarker(MarkerOptions()
                .position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                .title(it.name)).id
            markerList[internalId] = it
        }
    }
}

