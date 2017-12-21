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
import android.content.Context
import android.util.Base64

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

/**
 * preference 설정
 */
@SuppressLint("CommitPrefEdits")
inline fun Context.config(param: Preference): String? {
    val pref = getSharedPreferences("shared.pref", Context.MODE_PRIVATE)

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
        return value?.decodeBase64()
    }

    return null
}

inline fun Context.pref(param: Preference): String? {
    return config(param)
}

class Preference {
    lateinit var key: String
    var value: String? = null
    var write = false
    var async = false

    /**
     * read shared preference
     */
    fun read(key: String, value: String?) {
        data(key, value)
        write = false
    }

    /**
     * write shared preference
     */
    fun write(key: String, value: String?) {
        data(key, value)
        write = false
    }

    private fun data(key: String, value: String?) {
        this.key   = key
        this.value = value?.encodeBase64()
    }
}