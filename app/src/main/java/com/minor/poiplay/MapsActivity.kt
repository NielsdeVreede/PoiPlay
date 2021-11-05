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


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    private lateinit var popUpView: View
    private lateinit var titleText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /*Initialize frontend components*/
        popUpView = findViewById(R.id.popUpView)
        titleText = findViewById(R.id.titleText)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val eindhoven = LatLng(51.441642, 5.4697225)
        val defaultMarker = mMap.addMarker(MarkerOptions().position(eindhoven).title("Marker in Eindhoven"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eindhoven))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(eindhoven, 25.0f)
        )
        mMap.setOnMarkerClickListener( GoogleMap.OnMarkerClickListener {marker ->
            val markerName = marker.title
            Toast.makeText(this@MapsActivity, "Clicked location is $markerName", Toast.LENGTH_SHORT)
                .show()
            false})
        }
}