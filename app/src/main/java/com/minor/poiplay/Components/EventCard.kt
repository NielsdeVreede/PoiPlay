package com.minor.poiplay.Components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.minor.poiplay.R
import kotlinx.android.synthetic.main.event_card.view.*

class EventCard (
    context: Context?,
    attrs: AttributeSet? = null,
    amountOfAttendants: Int,
    eventTitle: String,
    eventTime: String
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.event_card, this)

        amount_of_attendants_title.text = amountOfAttendants.toString() + " aanwezig"
        event_card_title.text = eventTitle
        event_card_time.text = eventTime
    }
}