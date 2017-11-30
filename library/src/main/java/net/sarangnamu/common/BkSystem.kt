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

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.util.Base64
import android.util.Log
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
// ENVIRONMENT EXTENSION
//
////////////////////////////////////////////////////////////////////////////////////

/** sdcard 가 존재하는지 확인 */
inline fun Environment.hasSd() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/** data directory 에 사이즈 반환 */
inline fun Environment.dataDirSize() =
        BkSystem.blockSize(Environment.getDataDirectory()).toFileSizeString()

/** sdcard 사이즈 반환 */
inline fun Environment.sdSize() =
        BkSystem.blockSize(Environment.getExternalStorageDirectory()).toFileSizeString()

/** sdcard 경로 반환 */
inline fun Environment.sdPath() =
        Environment.getExternalStorageDirectory().getAbsolutePath()

////////////////////////////////////////////////////////////////////////////////////
//
//
//
////////////////////////////////////////////////////////////////////////////////////

/** try catch block */
inline fun <T, R> T.trycatch(block: (T) -> R) : R {
    try {
        return block(this)
    } catch (e: Exception) {
        Log.e("trycatch", "ERROR: " + e.message)
        throw e
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// SYSTEM
//
////////////////////////////////////////////////////////////////////////////////////

class BkSystem {
    companion object {
        @Suppress("DEPRECATION")
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