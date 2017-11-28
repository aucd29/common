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
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 22.. <p/>
 */

inline fun View.string(@StringRes resid: Int) = context.resources.getString(resid)

// https://antonioleiva.com/functional-operations-viewgroup-kotlin/
inline val ViewGroup.views: List<View>
    get() = (0..childCount - 1).map { getChildAt(it) }

abstract class FrameBase : FrameLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}

abstract class LinearBase : LinearLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}

abstract class RelativeBase : RelativeLayout {
    constructor(context: Context) : super(context) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.initLayout()
    }

    abstract fun initLayout()
}