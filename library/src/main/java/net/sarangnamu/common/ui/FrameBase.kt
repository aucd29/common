package net.sarangnamu.common.ui

import android.content.Context
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 27.. <p/>
 */

abstract class FrameBase : FrameLayout {
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

