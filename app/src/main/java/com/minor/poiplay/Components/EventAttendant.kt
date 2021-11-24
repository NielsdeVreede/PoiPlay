package com.minor.poiplay.Components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.minor.poiplay.R
import kotlinx.android.synthetic.main.event_attendant.view.*

class EventAttendant (
    context: Context?,
    attrs: AttributeSet? = null,
    name: String
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.event_attendant, this)

        name_initial_text.text = name.first().uppercaseChar().toString()
        full_name_text.text = name
    }
}