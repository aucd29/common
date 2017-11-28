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

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.WindowManager

// https://kotlinlang.org/docs/tutorials/android-plugin.html
import kotlinx.android.synthetic.main.dlg_license.*

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 */

class DialogParam(context: Context? = null, @StringRes title: Int = 0, @StringRes message: Int = 0) {
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
    var fullscreen = false
    var positive: ((DialogInterface) -> Unit)? = null
    var negative: ((DialogInterface) -> Unit)? = null

    @StringRes var positiveBtn = android.R.string.ok
    @StringRes var negativeBtn = android.R.string.cancel
    @LayoutRes var resid: Int = 0
}

fun Fragment.dialog(params: DialogParam): AlertDialog.Builder {
    return activity.dialog(params)
}

fun Activity.dialog(params: DialogParam): AlertDialog.Builder {
    val bd = AlertDialog.Builder(this)
    bd.setPositiveButton(params.positiveBtn, { d, i -> d.dismiss(); params.positive?.invoke(d) })

    params.title?.let { bd.setTitle(it) }
    params.message?.let { bd.setMessage(it) }
    params.negative?.let { bd.setNegativeButton(params.negativeBtn, { d, i -> d.dismiss(); it(d) }) }

    if (params.resid != 0) {
        bd.setView(params.resid)
    }

    return bd
}

fun Fragment.loading(params: DialogParam): ProgressDialog {
    return activity.loading(params)
}

fun Activity.loading(params: DialogParam): ProgressDialog {
    val bd = ProgressDialog(this)
    params.message?.let { bd.setMessage(it) }
    if (params.resid != 0) {
        bd.setContentView(params.resid)
    }

    return bd
}

fun Activity.showLicense(path: String = "file:///android_asset/license.html") {
    val dlg = dialog(DialogParam().apply {
        resid = R.layout.dlg_license
    }).show()

    dlg.title.roboto()
    dlg.web.loadUrl(path)
    dlg.fullscreen()
    dlg.show()
}

fun Dialog.fullscreen() {
    window.decorView.post { window.lpmm() }
}