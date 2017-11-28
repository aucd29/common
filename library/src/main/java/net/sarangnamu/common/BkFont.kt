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
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.TextView

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun TextView.roboto() {
    font("Roboto-Light")
}

inline fun TextView.font(name: String) {
    typeface = Typeface.createFromAsset(context.assets, "fonts/$name.ttf")
}

fun ViewGroup.font(name: String) {
    views.forEach {
        when (it) {
            is TextView -> it.font(name)
            is ViewGroup -> it.font(name)
        }
    }
}

class RobotoLightTextView: TextView {
    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initLayout()
    }

    protected fun initLayout() {
        roboto()
    }
}