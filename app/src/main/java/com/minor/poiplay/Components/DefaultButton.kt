package com.minor.poiplay.Components

import android.content.Context
import android.view.LayoutInflater
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.minor.poiplay.R

class DefaultButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle){

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.default_button, this, true)

    }


}