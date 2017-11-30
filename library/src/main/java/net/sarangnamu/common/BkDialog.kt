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
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog

// https://kotlinlang.org/docs/tutorials/android-plugin.html
import kotlinx.android.synthetic.main.dlg_license.*
import java.util.*

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 *
 * 그냥 anko 를 쓸까? -_ -;;
 */

class DialogParam(context: Context? = null, @StringRes message: Int = 0, @StringRes title: Int = 0) {
    init {
        context?.let {
            if (title != 0) {
                this.title = it.getString(title)
            }

            if (message != 0) {
                this.message = it.getString(message)
            }
        }
    }

    var title: String? = null
    var message: String? = null
    var positive: ((DialogInterface) -> Unit)? = null
    var negative: ((DialogInterface) -> Unit)? = null
    var textOnly: Boolean = false

    fun yesNo() {
        positiveBtn = android.R.string.yes
        negativeBtn = android.R.string.no
    }

    fun okCancel() {
        positiveBtn = android.R.string.ok
        negativeBtn = android.R.string.cancel
    }

    @StringRes var positiveBtn = android.R.string.ok
    @StringRes var negativeBtn = android.R.string.cancel
    @LayoutRes var resid: Int = 0
}

inline fun Fragment.dialog(params: DialogParam): AlertDialog.Builder {
    return activity.dialog(params)
}

inline fun Activity.dialog(params: DialogParam): AlertDialog.Builder {
    val bd = AlertDialog.Builder(this)

    with (params) { with (bd) {
        if (!textOnly) {
            setPositiveButton(positiveBtn, { d, i -> d.dismiss(); positive?.invoke(d) })
            negative?.let { setNegativeButton(negativeBtn, { d, i -> d.dismiss(); it(d) }) }
        }

        title?.let { setTitle(it) }
        message?.let { setMessage(it) }

        if (resid != 0) {
            setView(resid)
        }
    } }

    return bd
}

inline fun Activity.dialog(params: DialogParam, delayMillis: Long) {
    params.textOnly = true

    val dlg = dialog(params).show()
    window.decorView.postDelayed({ dlg.dismiss() }, delayMillis)
}

inline fun Fragment.loading(params: DialogParam): ProgressDialog {
    return activity.loading(params)
}

inline fun Activity.loading(params: DialogParam): ProgressDialog {
    val bd = ProgressDialog(this)

    with (params) { with (bd) {
        message?.let { setMessage(it) }
        if (resid != 0) {
            setContentView(resid)
        }
    } }

    return bd
}

inline fun Activity.showLicense(path: String = "file:///android_asset/license.html") {
    dialog(DialogParam().apply {
        resid = R.layout.dlg_license
    }).show().run {
        title.roboto()
        web.loadUrl(path)
        fullscreen()
        show()
    }
}

inline fun Dialog.fullscreen() = window.decorView.post { window.lpmm() }