/*
 * Copyright (C) Hanwha S&C Ltd., 2017. All rights reserved.
 *
 * This software is covered by the license agreement between
 * the end user and Hanwha S&C Ltd., and may be
 * used and copied only in accordance with the terms of the
 * said agreement.
 *
 * Hanwha S&C Ltd., assumes no responsibility or
 * liability for any errors or inaccuracies in this software,
 * or any consequential, incidental or indirect damage arising
 * out of the use of the software.
 */

@file:Suppress("NOTHING_TO_INLINE", "unused")
package net.sarangnamu.common

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.os.Build
import android.support.annotation.ColorRes
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun Animator.addEndListener(crossinline f: (Animator?) -> Unit) {
    addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {}
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) = f(animation)
    })
}

inline fun View.fadeColor(fcolor: Int, scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    ObjectAnimator.ofObject(this, "backgroundColor", ArgbEvaluator(), fcolor, scolor).apply {
        this.duration = duration
        f?.let { this.addEndListener(it) }
    }.start()
}

inline fun View.fadeColorRes(@ColorRes fcolor: Int, @ColorRes scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    fadeColor(context.color(fcolor), context.color(scolor), f, duration)
}

inline fun Window.fadeStatusBar(fcolor:Int, scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        val from = context.color(fcolor)
        val to   = context.color(scolor)

        ValueAnimator.ofArgb(from, to).apply {
            this.duration = duration
            this.addUpdateListener { statusBarColor = it.animatedValue as Int }
            f?.let { this.addEndListener(it) }
        }.start()
    }
}

inline fun Window.fadeStatusBarRes(@ColorRes fcolor: Int, @ColorRes scolor: Int, noinline f: ((Animator?) -> Unit)? = null, duration: Long = 500) {
    fadeStatusBar(context.color(fcolor), context.color(scolor), f, duration)
}