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

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.Base64
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.io.File

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

////////////////////////////////////////////////////////////////////////////////////
//
// CONTEXT EXTENTION
//
////////////////////////////////////////////////////////////////////////////////////

public fun Context.isForegroundApp(pkgName: String): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    return manager.runningAppProcesses.filter {
        it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                && it.processName == pkgName }.size == 1
}

public fun Context.isForegroundApp(): Boolean {
    return isForegroundApp(packageName)
}

public fun Context.externalFilePath() = getExternalFilesDir(null).absolutePath

public fun Context.displayDensity() = resources.displayMetrics.density

public fun Context.showKeyboard(view: View?) {
    view?.let {
        it.postDelayed({
            it.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(it, InputMethodManager.SHOW_FORCED)
        }, 400)
    }
}

public fun Context.hideKeyboard(view: View?) {
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

public fun Context.forceHideKeyboard(window: Window?) {
    window?.run { setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN) }
}

@SuppressLint("CommitPrefEdits")
public fun Context.config(param: Preference): String? {
    val pref = getSharedPreferences("burke.pref", Context.MODE_PRIVATE)

    if (param.write) {
        // write mode
        val editor = pref.edit().putString(param.key, param.value)
        if (param.async) {
            editor.apply()
        } else {
            editor.commit()
        }
    } else {
        // read mode
        val value = pref.getString(param.key, param.value)
        return value?.let { String(Base64.decode(it, Base64.DEFAULT)) } ?: value
    }

    return null
}

public class Preference {
    lateinit var key: String
    var value: String? = null
    var write = false
    var async = false

    fun read(key: String, value: String?) {
        data(key, value)
        write = false
    }

    fun write(key: String, value: String?) {
        data(key, value)
        write = false
    }

    private fun data(key: String, value: String?) {
        this.key = key
        this.value = value?.let { Base64.encodeToString(it.toByteArray(), Base64.DEFAULT) } ?: value
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// ENVIRONMENT EXTENSION
//
////////////////////////////////////////////////////////////////////////////////////

public fun Environment.hasSdcard() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

public fun Environment.dataDirectorySize() =
        BkSystem.blockSize(Environment.getDataDirectory()).toFileSizeString()

public fun Environment.sdSize() =
        BkSystem.blockSize(Environment.getExternalStorageDirectory()).toFileSizeString()

public fun Environment.sdPath() =
        Environment.getExternalStorageDirectory().getAbsolutePath()

////////////////////////////////////////////////////////////////////////////////////
//
// ACTIVITY EXTENSION
//
////////////////////////////////////////////////////////////////////////////////////

public fun Activity.killApp() {
    moveTaskToBack(true)
    finish()
    android.os.Process.killProcess(android.os.Process.myPid())
}

////////////////////////////////////////////////////////////////////////////////////
//
// SYSTEM
//
////////////////////////////////////////////////////////////////////////////////////

class BkSystem {
    companion object {
        fun blockSize(path: File): Long {
            val blockSize: Long
            val availableBlocks: Long
            val stat = StatFs(path.path)

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                blockSize       = stat.blockSizeLong
                availableBlocks = stat.availableBlocksLong
            } else {
                blockSize       = stat.blockSize.toLong()
                availableBlocks = stat.availableBlocks.toLong()
            }

            return availableBlocks * blockSize
        }
    }
}