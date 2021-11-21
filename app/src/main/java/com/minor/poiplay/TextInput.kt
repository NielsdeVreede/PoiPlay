package com.minor.poiplay

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.text_input.view.*

class TextInput(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.text_input, this)

        val customAttributesStyle =
            context.obtainStyledAttributes(attrs, R.styleable.text_input, 0, 0)

        try {
            input_label.text = customAttributesStyle.getString(R.styleable.text_input_label)
            input.hint = customAttributesStyle.getString(R.styleable.text_input_placeholder)
        } finally {
            customAttributesStyle.recycle()
        }

        input.doAfterTextChanged {
            onTextChange(input.text.toString())
        }
    }

    lateinit var onTextChange : (text: String) -> Unit
}