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
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 */

inline fun Context.color(name: String): ColorDrawable {
    val id = resources.getIdentifier(name, "color", packageName)
    return ColorDrawable(id)
}

class ColorSelector(context: Context): SelectorBase(context) {
    override fun drawable(name: String): Drawable {
        return context.color(name)
    }
}
