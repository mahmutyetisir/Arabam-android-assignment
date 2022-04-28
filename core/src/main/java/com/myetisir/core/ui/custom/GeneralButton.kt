package com.myetisir.core.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import com.google.android.material.button.MaterialButton
import com.myetisir.core.R
import com.myetisir.core.common.px

class GeneralButton constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs) {


    init {
        gravity = Gravity.CENTER

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.white))
            strokeColor = ColorStateList.valueOf(context.getColor(R.color.black))
            rippleColor =
                ColorStateList.valueOf(context.getColor(R.color.design_default_color_surface))
        }

        isAllCaps = false
        textAlignment = TEXT_ALIGNMENT_CENTER
        setTypeface(typeface, Typeface.BOLD)
        elevation = 0f
        strokeWidth = 4
        stateListAnimator = null

        minHeight = 45.px
    }
}