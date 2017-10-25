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

import java.io.File
import java.io.InputStream

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 17.. <p/>
 */

public fun File.copy(target: File, listener: FileListener? = null, overwrite:Boolean = false, buffSize:Int = DEFAULT_BUFFER_SIZE) {
    if (!exists()) {
        listener?.run {
            progress(true)
            result = FileListener.NOT_FOUND
        }

        return
    }

    if (isDirectory) {
        listener?.run {
            progress(true)
            result = FileListener.NOT_FILE
        }

        return
    }

    if (target.exists()) {
        if (overwrite) {
            target.delete()
        } else {
            listener?.run {
                progress(true)
                result = FileListener.EXIST_TARGET
            }

            return
        }
    }

    target.parentFile?.mkdirs()
    inputStream().copy(target, listener, overwrite, buffSize)
}

private fun InputStream.copy(target: File, listener: FileListener? = null, overwrite: Boolean = false, buffSize: Int = kotlin.io.DEFAULT_BUFFER_SIZE) {
    val buff  = ByteArray(buffSize)
    val osm   = target.outputStream()
    var bytes = read(buff)

    while (bytes >= 0) {
        osm.write(buff, 0, bytes)
        bytes = read(buff)

        listener?.run {
            current += bytes
            cal()

            progress(false)
        }

        if (listener != null && listener.cancel) {
            listener.result = FileListener.CANCELED
            break;
        }
    }

    listener?.run { progress(true) }
    osm.close()
    close()
}

public fun File.move(target:File):Boolean {
    if (!exists()) {
        return false;
    }

    if (target.exists()) {
        target.delete()
    }

    target.parentFile?.mkdirs()

    // rename 이 실패하면 파일을 복사한다.
    if (!renameTo(target)) {
        copy(target)
        delete()
    }

    return true
}

////////////////////////////////////////////////////////////////////////////////////
//
// FileListener
//
////////////////////////////////////////////////////////////////////////////////////

public abstract class FileListener {
    companion object {
        val SUCCESS   = 0
        val NOT_FOUND = -1
        val CANCELED  = -2
        val EXIST_TARGET = -3
        val NOT_FILE  = -4
        val UNKNOWN   = -9
    }

    var cancel = false
    var percent: Int = 0
    var current: Long = 0
    var total: Long = 0
    var result: Int = SUCCESS

    fun cal() {
        percent = (current / total * 100).toInt()
    }

    abstract fun progress(done: Boolean)
}