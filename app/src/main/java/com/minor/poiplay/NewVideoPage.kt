package com.minor.poiplay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.model.LatLng
import com.minor.poiplay.Components.CustomMapsLabel
import kotlinx.android.synthetic.main.new_video_page.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class NewVideoPage : Fragment(R.layout.new_video_page) {
    private val args: NewVideoPageArgs by navArgs()

    private val defaultUrl = "http://192.168.178.149:3000";
    private lateinit var queue: RequestQueue;



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(requireContext())

        title_text.text = args.name

        new_video.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                getResult.launch(takeVideoIntent)
            }
        }


        var videos: List<VideoEntity>

        val stringRequest = StringRequest(
            Request.Method.GET, "$defaultUrl/video/poi/${args.poiId}",
            { response ->
                //Convert json to list and add markers to map
                videos = Json.decodeFromString(response)
                println(videos)
            },
            {  error ->
                println(error)
            })
        queue.add(stringRequest)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value: Uri = it.data!!.data as Uri
                println(value.path)
                createVideo(value.toString())
            }
        }

    private fun createVideo(uri: String) {
        val params = HashMap<String,String>()
        params["poi_id"] = args.poiId.toString()
        params["username"] = "default"
        params["uri"] = uri
        val jsonObject = JSONObject(params as Map<*, *>)

        val req = JsonObjectRequest(
            Request.Method.POST,"$defaultUrl/video", jsonObject,
            { response ->
            },
            { error ->
                default_video_view.setVideoURI(uri.toUri())
                default_video_view.start()
            })
        queue.add(req)
    }
}