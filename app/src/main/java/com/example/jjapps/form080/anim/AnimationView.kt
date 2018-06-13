package com.example.jjapps.form080.anim

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.example.jjapps.form080.R

/**
 * Created by jgutierrez on 3/23/2018.
 */

class AnimationView{

    companion object {

        fun animationCheck(v: View, mContext:Context){
            val myAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_checked)
            v.animation = myAnim
            v.startAnimation(myAnim)
        }

        fun animationOut(v: View, mContext:Context){
            val myAnim = AnimationUtils.loadAnimation(mContext, R.anim.anim_out)
            v.animation = myAnim
            v.startAnimation(myAnim)
        }

        fun animationScaleUp(v: View, mContext:Context){
            val myAnim = AnimationUtils.loadAnimation(mContext, R.anim.scale_up)
            v.animation = myAnim
            v.startAnimation(myAnim)
        }

        fun animationScaleDown(v: View, mContext:Context){
            val myAnim = AnimationUtils.loadAnimation(mContext, R.anim.scale_down)
            v.animation = myAnim
            v.startAnimation(myAnim)
        }

        fun animationLeftToRigth(v: View, mContext:Context){
            val myAnim = AnimationUtils.loadAnimation(mContext, R.anim.animation_left_to_rigth)
            v.animation = myAnim
            v.startAnimation(myAnim)
        }

    }
}