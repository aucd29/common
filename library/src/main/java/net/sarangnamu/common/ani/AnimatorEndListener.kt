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

package net.sarangnamu.common.ani

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.util.Log
import android.widget.LinearLayout

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

inline fun Animator.addEndListener(crossinline f: (Animator?) -> Unit) {
    addListener(object : AnimatorListener {
        override fun onAnimationStart(p0: Animator?) {}
        override fun onAnimationCancel(p0: Animator?) {}
        override fun onAnimationRepeat(p0: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) = f(animation)
    })
}
