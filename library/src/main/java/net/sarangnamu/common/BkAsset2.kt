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

import android.content.Context
import android.util.Log
import java.io.File

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 28.. <p/>
 *
 * https://blog.uzuki.live/kotlin-copy-assets-internal-storage/
 */

class BkAsset2 {
    companion object {
//        fun copy(info: FileInfo) {
//            BkApp.context.assets.list(info.src).tryCatch {
//                if (it.isEmpty()) {
//                    copyFile(info)
//                    return
//                }
//
//                File(info.dest).mkdirs()
//                it.forEach { path ->
//                    if (!info.src.endsWith("/")) {
//                        info.src += "/"
//                    }
//
//                    info.src += path
//                    copyFile(info)
//                }
//            }
//        }
//
//        fun copyFile(info: FileInfo) {
//            BkApp.context.assets.open(info.src).use {
//                input -> File(info.dest).outputStream().use {
//                    var bytesCopied: Long = 0
//                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
//                    var bytes = input.read(buffer)
//
//                    while (bytes >= 0) {
//                        it.write(buffer, 0, bytes)
//                        bytesCopied += bytes
//                        bytes = input.read(buffer)
//
//                        info.listener?.progress(bytes, bytesCopied, )
//                    }
//
//                    info.listener?.finish("")
//                }
//            }
//        }
//
////        fun copyDir(info: FileInfo) {
////            val context = BkApp.context
////            val res = context.createPackageContext(context.packageName, 0).resources
////        }
////
////        fun copyFile() {
////
////        }
//
////        private fun copyAll(info: FileInfo) {
////            val context = BkApp.context
////            val res = context.createPackageContext(context.packageName, 0).resources
////            val am = res.assets
////            val assets = am.list(info.srcPath)
////
////            try {
////                if (assets.size == 0) {
////                    copyFile()
////                }
////            } catch (e: Exception) {
////
////            }
////        }
//
//
//        inline fun <T, R> T.tryCatch(block: (T) -> R): R {
//            try {
//                return block(this)
//            } catch (e: Exception) {
//                Log.e("BK", e.message)
//                throw e
//            }
//        }
    }

    data class FileInfo (
        var src: String,
        var dest: String,
        var listener: ProgressListener? = null
    )

    interface CopyListener {
        fun finish(name: String)
        fun error(message: String)
        fun isCancelled(): Boolean
        fun canceled()
    }

    interface ProgressListener : CopyListener {
        fun progress(read: Int, total: Long, percent: Int)
        fun fileSize(size: Long)
        fun fileSize(): Long
    }
}