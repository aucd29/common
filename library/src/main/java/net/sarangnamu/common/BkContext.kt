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

import android.app.ActivityManager
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

/**
 * pkgName 에 해당하는 앱이 foreground 인지 확인
 */
inline fun Context.isForegroundApp(pkgName: String): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses.filter {
        it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && it.processName == pkgName }.size == 1
}

/**
 * 현재 앱이 foreground 인지 확인
 */
inline fun Context.isForegroundApp(): Boolean {
    return isForegroundApp(packageName)
}

/**
 * sdcard 내 app 경로 전달
 */
inline fun Context.externalFilePath() = getExternalFilesDir(null).absolutePath

/**
 * display density 반환
 */
inline fun Context.displayDensity() = resources.displayMetrics.density

/**
 * open keyboard
 */
inline fun Context.showKeyboard(view: View?) {
    view?.let {
        it.postDelayed({
            it.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, InputMethodManager.SHOW_FORCED)
        }, 400)
    }
}

/**
 * hide keyboard
 */
inline fun Context.hideKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

/**
 * force hide keyboard
 */
inline fun Context.forceHideKeyboard(window: Window?) {
    window?.run { setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) }
}