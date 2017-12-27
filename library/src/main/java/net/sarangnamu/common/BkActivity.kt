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
import android.os.AsyncTask
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 29.. <p/>
 */

// https://blog.asamaru.net/2015/12/15/android-app-finish/
inline fun Activity.processKill() {
    moveTaskToBack(true);
    ActivityCompat.finishAffinity(this)
    android.os.Process.killProcess(android.os.Process.myPid())
}

/**
 * app 강제 종료
 */
inline fun Activity.kill() {
    try {
        System.runFinalizersOnExit(true)
    } catch (ignored: Exception) {
    } finally {
        System.exit(0)
    }
}

inline fun Activity.toast(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
inline fun Activity.toastLong(msg: String): Toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
inline fun Activity.snackbar(view: View, msg: String): Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
inline fun Activity.snackbarLong(view: View, msg: String): Snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)

inline fun Activity.toast(@StringRes resid: Int): Toast = toast(getString(resid))
inline fun Activity.toastLong(@StringRes resid: Int): Toast = toastLong(getString(resid))
inline fun Activity.snackbar(view: View, @StringRes resid: Int): Snackbar = snackbar(view, getString(resid))
inline fun Activity.snackbarLong(view: View, @StringRes resid: Int): Snackbar = snackbarLong(view, getString(resid))
inline fun Activity.clickExitAgain(view: View?) = AppCloser(this, view)

open class AppCloser(var activity: Activity, var view: View? = null) {
    companion object {
        val delay = 2000
    }

    var pressedTime: Long = 0
    var toast: Toast? = null
    var snackbar: Snackbar? = null

    inline fun time() = pressedTime + delay

    fun onBackPressed() {
        if (System.currentTimeMillis() > time()) {
            pressedTime = System.currentTimeMillis()
            show()

            return
        }

        if (System.currentTimeMillis() <= time()) {
            view?.let { snackbar?.dismiss() } ?: toast?.cancel()
            ActivityCompat.finishAffinity(activity)
        }
    }

    fun show() {
        if (view != null) {
            activity.snackbar(view!!, R.string.activity_click_exit_again)
            snackbar?.show()
        } else {
            toast = activity.toast(R.string.activity_click_exit_again)
            toast?.show()
        }
    }
}

fun Activity.async(background: (() -> Boolean)? = null, post: ((result: Boolean) -> Unit)? = null) {
    Async(background, post).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
}