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

package net.sarangnamu.common

import android.graphics.Bitmap
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView

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

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

/**
 * https://antonioleiva.com/kotlin-ongloballayoutlistener/
 */
@Suppress("DEPRECATION")
inline fun View.layoutListener(crossinline f: () -> Unit) = with (viewTreeObserver) {
    addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            } else {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }

            f()
        }
    })
}

fun View.capture(): Bitmap? {
    clearFocus()
    isPressed = false

    val cacheDrawing = willNotCacheDrawing()
    setWillNotCacheDrawing(false)
    invalidate()
    buildDrawingCache()

    val color = drawingCacheBackgroundColor
    drawingCacheBackgroundColor = 0

    if (color != 0) {
        destroyDrawingCache()
    }

    val cache = drawingCache
    if (cache == null) {
        return null
    }

    val bmp = Bitmap.createBitmap(cache)
    destroyDrawingCache()
    setWillNotCacheDrawing(cacheDrawing)
    drawingCacheBackgroundColor = color

    return bmp
}

fun TextView.gravityCenter() {
    gravity = Gravity.CENTER
}