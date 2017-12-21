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

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout

// https://kotlinlang.org/docs/tutorials/android-plugin.html
import kotlinx.android.synthetic.main.dlg_web.*
import kotlinx.android.synthetic.main.dlg_web.view.*

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 *
 * 그냥 anko 를 쓸까? -_ -;;
 */

class DialogParam(@StringRes message: Int = 0, @StringRes title: Int = 0): DlgParam(message) {
    var title: String? = BkApp.context().string(title)

    var positive: ((DialogInterface) -> Unit)? = null
    var negative: ((DialogInterface) -> Unit)? = null
    var hideButton: Boolean = false

    fun yesNo() {
        positiveText = android.R.string.yes
        negativeText = android.R.string.no
    }

    fun okCancel() {
        positiveText = android.R.string.ok
        negativeText = android.R.string.cancel
    }

    @StringRes var positiveText = android.R.string.ok
    @StringRes var negativeText = android.R.string.cancel
}

open class LoadingParam(@StringRes message: Int = 0): DlgParam(message) {
    var style_horizontal = false
}

open class DlgParam(@StringRes message: Int = 0) {
    var message: String? = BkApp.context().getString(message)
    @LayoutRes var resid: Int = 0
}

inline fun Fragment.dialog(params: DialogParam): AlertDialog.Builder {
    return activity.dialog(params)
}

inline fun Activity.dialog(params: DialogParam): AlertDialog.Builder {
    val bd = AlertDialog.Builder(this)

    with (params) { with (bd) {
        if (!hideButton && resid == 0) {
            setPositiveButton(positiveText, { d, i -> d.dismiss(); positive?.invoke(d) })
            negative?.let { setNegativeButton(negativeText, { d, i -> d.dismiss(); it(d) }) }
        }

        title?.let { setTitle(it) }
        message?.let { setMessage(it) }

        if (resid != 0) {
            setView(resid)
        }
    } }

    return bd
}

inline fun Activity.dialog(params: DialogParam, killTimeMillis: Long) {
    val dlg = dialog(params).show()
    window.decorView.postDelayed({ dlg.dismiss() }, killTimeMillis)
}

inline fun Fragment.loading(params: LoadingParam): ProgressDialog {
    return activity.loading(params)
}

inline fun Activity.loading(params: LoadingParam): ProgressDialog {
    val bd = ProgressDialog(this)

    if (params.style_horizontal) {
        bd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
    } else {
        bd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    with (params) { with (bd) {
        message?.let { setMessage(it) }
        if (resid != 0) {
            setView(layoutInflater.inflate(resid, null))
        }
    } }

    return bd
}

inline fun Activity.browser(url: String, @StringRes titleId: Int = 0) {
    dialog(DialogParam().apply {
        resid = R.layout.dlg_web
        if (titleId != 0) {
            title = string(titleId)
        }
    }).show().run {
        web.run {
            loadUrl(url)
            web.layoutParams = FrameLayout.LayoutParams(BkApp.screenX(), BkApp.screenY())
        }
        fullscreen()
    }
}

inline fun Dialog.fullscreen() {
    window.run {
        val attr = attributes
        attr.run {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }
        attributes = attr

        setBackgroundDrawableResource(android.R.color.transparent)
    }
}

inline fun ProgressDialog.progressFileSize(unit: Int, value: Long) {
    setProgress((value shr (10 * unit)).toInt())
    setProgressNumberFormat(value.toFileSizeString(unit))
}


