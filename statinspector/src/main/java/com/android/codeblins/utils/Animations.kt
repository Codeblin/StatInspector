package com.android.codeblins.utils

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

object Animations {
    fun rotate(target: View, radius: Float) {
        val anim = ObjectAnimator.ofFloat(target, "rotation", radius)
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }
}