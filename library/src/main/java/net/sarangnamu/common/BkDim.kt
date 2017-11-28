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

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

inline fun Float.pixelToDp()    = this / BkApp.context().displayDensity()
inline fun Float.dpToPixel()    = this * BkApp.context().displayDensity()
inline fun Float.doToPixelInt() = (this * BkApp.context().displayDensity()).toInt()

inline fun Int.pixelToDp()      = this / BkApp.context().displayDensity()
inline fun Int.dpToPixel()      = this * BkApp.context().displayDensity()
inline fun Int.doToPixelFloat() = (this * BkApp.context().displayDensity())

class BkDim {
    companion object {
        fun dpToPixel(dp1: Float, dp2: Float, dp3: Float, dp4: Float) =
            listOf(dp1.dpToPixel(), dp2.dpToPixel(), dp3.dpToPixel(), dp4.dpToPixel())

        fun dpToPixel(dpList: List<Float>) =
            listOf(dpList.get(0).dpToPixel(), dpList.get(1).dpToPixel(),
                   dpList.get(2).dpToPixel(), dpList.get(3).dpToPixel())

        fun dpToPixelInt(dp1: Float, dp2: Float, dp3: Float, dp4: Float) =
            listOf(dp1.doToPixelInt(), dp2.doToPixelInt(), dp3.doToPixelInt(), dp4.doToPixelInt())

        fun dpToPixelInt(dpList: List<Float>) =
            listOf(dpList.get(0).doToPixelInt(), dpList.get(1).doToPixelInt(),
                   dpList.get(2).doToPixelInt(), dpList.get(3).doToPixelInt())
    }
}