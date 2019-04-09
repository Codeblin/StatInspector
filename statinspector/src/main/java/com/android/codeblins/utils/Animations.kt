package com.android.codeblins.utils

import android.animation.ObjectAnimator
import android.os.Build
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

object Animations {
    fun rotate(target: View, radius: Float) {
        val anim = ObjectAnimator.ofFloat(target, "rotation", radius)
        anim.interpolator = DecelerateInterpolator()
        anim.start()
    }

    fun moveWindow(set: Boolean, c1: ConstraintSet, c2: ConstraintSet, target: ConstraintLayout): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1.0f)

            TransitionManager.beginDelayedTransition(target, transition)
            val constraint: ConstraintSet
            val gravity: Int

            val layoutParams = target.layoutParams as FrameLayout.LayoutParams

            if (set) {
                constraint = c1
                gravity = Gravity.TOP
            } else {
                constraint = c2
                gravity = Gravity.BOTTOM
            }

            layoutParams.gravity = gravity
            constraint.applyTo(target)
            return !set
        }

        return false
    }
}