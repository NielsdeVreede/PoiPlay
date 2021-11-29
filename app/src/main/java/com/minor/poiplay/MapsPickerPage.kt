package com.minor.poiplay

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.minor.poiplay.Components.CustomMapsLabel
import kotlinx.android.synthetic.main.maps_page.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class MapsPickerPage : Fragment(R.layout.maps_page) {
    private val markerList: MutableMap<String, PoiEntity> = mutableMapOf()
    private val defaultUrl = SettingsFactory.getDefaultUrl()
    private lateinit var queue: RequestQueue;
    private var clickedLocationLat = 0.0f;
    private var clickedLocationLong = 0.0f;

    private val callback = OnMapReadyCallback { googleMap ->
        queue = Volley.newRequestQueue(requireContext())
        val stringRequest = StringRequest(
            Request.Method.GET, "$defaultUrl/poi",
            { response ->
                //Convert json to list and add markers to map
                val listOfPOIs = Json.decodeFromString<List<PoiEntity>>(response)

                googleMap.setInfoWindowAdapter(getContext()?.let { CustomMapsLabel(it) })
                addMarkersToMap(listOfPOIs, googleMap)

                val lastPOI = listOfPOIs.last()
                centerMapAroundLatLng(
                    LatLng(
                        lastPOI.latitude.toDouble(),
                        lastPOI.longitude.toDouble()
                    ), googleMap
                )
            },
            { error ->
                println(error)
            })
        queue.add(stringRequest)


        googleMap.setOnMarkerClickListener { marker ->
            if (popup_view.visibility == View.GONE) {
                popup_view.visibility = View.VISIBLE
            }
            location_title.text = marker.title
            clickedLocationLat = marker.position.latitude.toFloat()
            clickedLocationLong = marker.position.longitude.toFloat()

            val customId = markerList[marker.id]?.id as Int
            updateAttendance(customId)

            val getPOIEventsRequest = StringRequest(
                Request.Method.GET, "$defaultUrl/event/poi/$customId",
                { response ->
                    println(response)
                },
                { error ->
                    println(error)
                })
            queue.add(getPOIEventsRequest)

            attend_button.onClick = {
                val params = HashMap<String, String>()
                params["poi_id"] = customId.toString()
                val jsonObject = JSONObject(params as Map<*, *>)
                val req = JsonObjectRequest(
                    Request.Method.POST, "$defaultUrl/attendance", jsonObject,
                    { response ->
                        updateAttendance(customId)
                    },
                    { error ->
                        updateAttendance(customId)
                    })
                queue.add(req)
            }

            create_event_button.setOnClickListener {
                findNavController().navigate(
                    MapsPickerPageDirections.actionMapsPickerPageToCreateEventPage(
                        clickedLocationLat,
                        clickedLocationLong,
                        customId
                    )
                )
            }

//            view_video_feed.setOnClickListener {
//                findNavController().navigate(
//                    MapsPickerPageDirections.actionMapsPickerPageToNewVideoPage(
//                        customId,
//                        markerList[marker.id]?.name.toString()
//                    )
//                )
//            }
            false
        }
    }

    private fun updateAttendance(poi_id: Int) {
        val req = StringRequest(
            Request.Method.GET, "$defaultUrl/attendance/$poi_id",
            { response ->
                present_people_text.text = response
            },
            { error ->
                present_people_text.text = "ERROR"
                println(error)
            })
        queue.add(req)
    }

    private fun centerMapAroundLatLng(centerPoint: LatLng, mMap: GoogleMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(centerPoint))
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(centerPoint, 15.5f)
        )
    }

    private fun addMarkersToMap(listOfPOIs: List<PoiEntity>, mMap: GoogleMap) {
        listOfPOIs.forEach {
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                    .icon(getContext()?.let { it1 ->
                        bitmapDescriptorFromVector(
                            it1,
                            R.drawable.soccer_location_marker
                        )
                    })
                    .title(it.name)
            )
            marker.showInfoWindow()
            markerList[marker.id] = it
        }
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        attend_button.setText("Ik ga hier ook naar toe")
    }
}