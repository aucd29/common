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

package net.sarangnamu.common.ui

import android.content.Context
import android.os.Build
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import net.sarangnamu.common.BkDim

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 28.. <p/>
 */

abstract class LinearBase : LinearLayout {
    constructor(context: Context) : super(context) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initLayout()
    }

    protected fun string(@StringRes resid: Int) = context.resources.getString(resid)

    ////////////////////////////////////////////////////////////////////////////////////
    //
    // abstract
    //
    ////////////////////////////////////////////////////////////////////////////////////

    abstract fun initLayout()
}