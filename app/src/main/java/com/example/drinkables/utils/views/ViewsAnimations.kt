package com.example.drinkables.utils.views

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import kotlin.math.abs

const val NORMAL_SCALE = 1.0f
const val MIN_SCALE = 0.05f
const val SCALE_FACTOR = 1.5f

@SuppressLint("Recycle")
fun View.startJellyAnimation(duration: Long, scale: Float) {
    val newScale = NORMAL_SCALE + scale
    val widthAnimator = ObjectAnimator.ofFloat(this, View.SCALE_X, newScale).setDuration(duration)
    val heightAnimator = ObjectAnimator.ofFloat(this, View.SCALE_Y, newScale).setDuration(duration)
    val setAnimator = AnimatorSet()
    val view = this
    with(setAnimator){
        this.duration = duration
        playTogether(widthAnimator, heightAnimator)
        addListener(
            object : AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    if (abs(scale) > MIN_SCALE) {
                        view.startJellyAnimation(duration, scale / SCALE_FACTOR * -1f)
                    } else {
                        widthAnimator.setFloatValues(NORMAL_SCALE)
                        heightAnimator.setFloatValues(NORMAL_SCALE)
                        setAnimator.playTogether(widthAnimator, heightAnimator)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {}
            })
        start()
    }
}