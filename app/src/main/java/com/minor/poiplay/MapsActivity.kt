package com.minor.poiplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.minor.poiplay.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var mapFragment: SupportMapFragment
    private lateinit var popUpView: View
    private lateinit var titleText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val


        /*Initialize frontend components*/
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        popUpView = findViewById(R.id.popUpView)
        popUpView.visibility = View.INVISIBLE
        titleText = findViewById(R.id.titleText)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val eindhoven = LatLng(51.441642, 5.4697225)
        mMap.addMarker(MarkerOptions().position(eindhoven).title("Cruyff veld Eindhoven"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eindhoven))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(eindhoven, 20.0f)
        )
        mMap.setOnMarkerClickListener { marker ->

            if (popUpView.visibility == View.INVISIBLE){
                popUpView.visibility = View.VISIBLE
            }
            val markerName = marker.title
            titleText.text = markerName


            false
        }
    }
}