/*
 * Copyright 2016 Burke Choi All rights reserved.
 *             http://www.sarangnamu.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE", "unused")
package net.sarangnamu.common

import android.content.res.AssetManager
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 10. 17.. <p/>
 */

/**
 * 파일이나 디렉토리의 전체 크기를 반환 한다
 */
fun File.totalLength(): Long {
    if (!exists()) {
        return 0
    }

    var total: Long = 0
    if (isDirectory) {
        listFiles().forEach {
            if (it.isDirectory) {
                total += it.totalLength()
            } else {
                total += it.length()
            }
        }
    } else {
        total = this.length()
    }

    return total
}

/**
 * 파일이나 디렉토리를 복사 한다 (하위 폴더 포함)
 */
fun File.copy(target: File, listener:FileListener? = null) {
    val log = LoggerFactory.getLogger(File::class.java)

    if (!this.exists()) {
        listener?.finish(FileListener.NOT_FOUND)
        return
    }

    listener?.run {
        if (total == 0L) {
            total = totalLength()
        }
    }

    if (isDirectory) {
        listFiles().forEach {
            if (it.isDirectory) {
                it.copy(File(target, "${it.parentFile.name}/${it.name}"), listener)
            } else {
                if (!target.exists()) {
                    target.mkdirs()
                }

                if (log.isTraceEnabled()) {
                    log.trace("${it.absolutePath} -> ${target.absolutePath}/${it.name}")
                }

                it.inputStream().use { ism ->
                    ism.copyFile(File(target, it.name), listener)
                }
            }
        }
    } else {
        if (!target.exists()) {
            target.mkdirs()
        }

        if (log.isTraceEnabled()) {
            log.trace("${this.absolutePath} -> ${target.absolutePath}/${this.name}")
        }

        this.inputStream().use { ism ->
            ism.copyFile(File(target, this.name), listener)
        }
    }
}

private fun InputStream.copyFile(target: File, listener: FileListener? = null) {
    if (target.exists()) {
        target.delete()
    }

    target.outputStream().use { osm ->
        val buff  = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = read(buff)

        while (bytes >= 0) {
            osm.write(buff, 0, bytes)

            listener?.run {
                current += bytes
                progress()
                trace()
            }

            // 이거 이쁘게 하는 방법이 없나. -_ -;
            // jump 는 while 갈 수가 없고...
            if (listener != null && listener.cancel) {
                // stop file copy event
                listener.finish(FileListener.CANCELED)
                break;
            }

            bytes = read(buff)
        }

        listener?.run {
            if (cancel) {
                return
            }

            if (total == 0L) {
                finish(FileListener.DONE)
            } else {
                if (percent == 100) {
                    finish(FileListener.DONE)
                }
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////
//
// ASSETS
//
////////////////////////////////////////////////////////////////////////////////////

fun AssetManager.copy(srcPath: String, destPath: String, listener: FileListener? = null) {
    val list = this.list(srcPath)
    listener?.let {
        if (it.total == 0L) {
            it.total = this.totalLength(srcPath)
        }
    }

    if (list.size == 0) {
        this.open(srcPath).use {
            val f = File(destPath, srcPath)
            if (!f.parentFile.exists()) {
                f.parentFile.mkdirs()
            }

            this.open(srcPath).copyFile(f, listener)
        }
    } else {
        list.forEach {
            this.copy("$srcPath/$it", destPath, listener)
        }
    }
}

fun AssetManager.totalLength(srcPath: String): Long {
    val list = this.list(srcPath)
    var length: Long = 0
    if (list.size == 0) {
        this.open(srcPath).use {
            val buff  = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytes = 0

            do {
                length += bytes
                bytes = it.read(buff)
            } while (bytes >= 0)
        }

        return length
    } else {
        list.forEach {
            length += this.totalLength("$srcPath/$it")
        }
    }

    return length
}

////////////////////////////////////////////////////////////////////////////////////
//
// FileListener
//
////////////////////////////////////////////////////////////////////////////////////

abstract class FileListener {
    private val log = LoggerFactory.getLogger(FileListener::class.java)

    companion object {
        val DONE = 1
        val NOT_FOUND = -1
        val CANCELED  = -2
    }

    var cancel = false
    var percent: Int = 0
    var current: Long = 0
    var total: Long = 0
    val ignore: Boolean = false

    open fun progress() {
        if (total != 0L && !ignore) {
            percent = (current.toDouble() / total.toDouble() * 100.0).toInt()
        }
    }

    fun trace() {
        if (log.isTraceEnabled()) {
            log.trace("$percent% (${current.toFileSizeString()} / ${total.toFileSizeString()})")
        }
    }

    abstract fun finish(code: Int)
}