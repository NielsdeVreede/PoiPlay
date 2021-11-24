package com.minor.poiplay

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.new_video_page.*

class NewVideoPage : Fragment(R.layout.new_video_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        record_button.setOnClickListener {
            Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
                getResult.launch(takeVideoIntent)
            }
        }
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val value: Uri = it.data!!.data as Uri
                println(value.toString())
                default_video_view.setVideoURI(value)
                default_video_view.start()
            }
        }
}