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

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

inline fun Long.unitCount(): Int {
    var u = 0
    var size = this

    while (size > 1024 * 1024) {
        u++
        size = size shr 10
    }

    if (size > 1024) {
        u++
    }

    return u
}

inline fun Long.shr10(unit: Int): Int {
    return (this shr (10 * unit)).toInt()
}

inline fun Long.toFileSizeString(unit: Int): String {
    val size = this shr (10 * unit)

    return String.format("%.1f %cB", size / 1024f, " KMGTPE"[unit])
}

inline fun Long.toFileSizeString(): String {
    var u = 0
    var size = this

    while (size > 1024 * 1024) {
        u++
        size = size shr 10
    }

    if (size > 1024) {
        u++
    }

    return String.format("%.1f %cB", size / 1024f, " KMGTPE"[u])
}

inline fun Double.toFileSizeString(unit: Int): String {
    val size = this.toLong() shr (10 * unit)

    return String.format("%.1f %cB", size / 1024f, " KMGTPE"[unit])
}

inline fun Double.toFileSizeString(): String {
    var u = 0
    var size = this.toLong()

    while (size > 1024 * 1024) {
        u++
        size = size shr 10
    }

    if (size > 1024) {
        u++
    }

    return String.format("%.1f %cB", size / 1024f, " KMGTPE"[u])
}