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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.TextView

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 2.. <p/>
 */

/**
 * https://antonioleiva.com/kotlin-ongloballayoutlistener/\
 * https://stackoverflow.com/questions/38827186/what-is-the-difference-between-crossinline-and-noinline-in-kotlin
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

inline fun View.capture(): Bitmap? {
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

inline fun TextView.gravityCenter() {
    gravity = Gravity.CENTER
}

inline fun Context.drawable(name: String): Drawable {
    val id = resources.getIdentifier(name, "drawable", packageName)
    return ContextCompat.getDrawable(this, id)
}

inline fun StateListDrawable.normalState(drawable: Drawable) {
    addState(intArrayOf(), drawable)
}

inline fun StateListDrawable.pressedState(drawable: Drawable) {
    addState(intArrayOf(android.R.attr.state_pressed), drawable)
}

inline fun StateListDrawable.disabledState(drawable: Drawable) {
    addState(intArrayOf(-android.R.attr.state_enabled), drawable)
}

class DrawableSelector(context: Context): SelectorBase(context) {
    override fun drawable(name: String): Drawable {
        return context.drawable(name)
    }
}

abstract class SelectorBase(var context: Context) {
    companion object {
        val MASK: Int        = 0X1
        val TP_DISABLED: Int = 0x1
        val TP_PRESSED: Int  = 0x2
        val TP_NORMAL: Int   = 0x3

        val TP_DEFAULT: Int  = TP_NORMAL or TP_PRESSED
        val TP_ALL: Int      = TP_DISABLED or TP_PRESSED or TP_NORMAL
    }

    var disableSuffix = "_disabled"
    var pressedSuffix = "_pressed"
    var normalSuffix  = "_normal"

    fun select(name: String, type: Int): StateListDrawable {
        val drawable = StateListDrawable()

        (0..3).forEach { i ->
            when (type and (MASK shl i)) {
                TP_NORMAL   -> drawable.normalState(drawable(name + normalSuffix))
                TP_PRESSED  -> drawable.pressedState(drawable(name + pressedSuffix))
                TP_DISABLED -> drawable.disabledState(drawable(name + disableSuffix))
            }
        }

        return drawable
    }

    abstract fun drawable(name : String): Drawable
}

inline fun Window.statusBar(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setStatusBarColor(color)
    }
}

