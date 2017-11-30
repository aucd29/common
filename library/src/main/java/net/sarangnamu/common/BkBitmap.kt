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
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

//https://github.com/mohamad-amin/Android-FastSearch/blob/master/app/src/main/java/com/mohamadamin/fastsearch/free/utils/BitmapUtils.java

fun Drawable.bitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val bmp = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
    val canvas = Canvas(bmp)

    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)

    return bmp
}

fun Bitmap.resize(w: Int, h: Int): Bitmap {
    val matrix = Matrix()
    val scaleW = (w / width).toFloat()
    val scaleH = (h / height).toFloat()

    matrix.postScale(scaleW, scaleH)

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}