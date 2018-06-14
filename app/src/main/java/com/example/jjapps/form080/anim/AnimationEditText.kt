package com.example.jjapps.form080.anim

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.widget.TextView
import xyz.goldapp.jjapps.form080.R

/**
 * Created by jgutierrez on 3/19/2018.
 */
class AnimationEditText {

    companion object {
        private val vibrate: Int = R.anim.vibration_animation;

        fun animateVibration(edt: EditText, context: Context){
            val fadeOutAndroidAnimation = AnimationUtils.loadAnimation(context, vibrate)

            edt.startAnimation(fadeOutAndroidAnimation)
            edt.setTextColor(context.getResources().getColor(R.color.colorRed))

            edt.animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            edt.setTextColor(context.getResources().getColor(R.color.colorTextGray))
                        }
                    })

        }

        fun animateVibrationText(edt: TextView, context: Context){
            val fadeOutAndroidAnimation = AnimationUtils.loadAnimation(context, vibrate)

            edt.startAnimation(fadeOutAndroidAnimation)
            edt.setTextColor(context.getResources().getColor(R.color.colorRed))

            edt.animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            edt.setTextColor(context.getResources().getColor(R.color.colorTextGray))
                        }
                    })

        }
    }

}