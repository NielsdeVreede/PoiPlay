package com.minor.poiplay.Components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.minor.poiplay.R
import kotlinx.android.synthetic.main.default_button.view.*

class DefaultButton(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.default_button, this)

        button.setOnClickListener{
            onClick()
        }
    }

    lateinit var onClick : () -> Unit

    fun setText(text: String) {
        button.text = text
    }
}