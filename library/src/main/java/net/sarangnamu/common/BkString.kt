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

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.io.File

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

/**
 * 문자열 끝에 / 이 존재하는지 확인
 */
inline fun CharSequence.isLastSlash(): Boolean =
        !isEmpty() && lastIndexOf(File.separator) != -1

/**
 * 문자열 끝에 / 를 붙임
 */
inline fun String.addLastSlash(): String {
    if (isEmpty()) {
        return this
    }

    if (!endsWith(File.separator)) {
        return this + File.separator
    }

    return this
}

/**
 * 파일명.확장자 반환
 */
inline fun String.fileFullName(): String = substringAfterLast('/')

/**
 * 파일 경로만 반환
 */
inline fun String.filePath(): String = substringBeforeLast('/', "")

/**
 * 파일이름만 반환
 */
inline fun String.fileName(): String = substringAfterLast('/')

/**
 * 파일 확장자만 반환
 */
inline fun String.fileExtension(): String = fileFullName().substringBeforeLast('.')

inline fun String.encodeBase64(): String = Base64.encodeToString(this.toByteArray(), Base64.DEFAULT)
inline fun String.decodeBase64(): String = String(Base64.decode(this, Base64.DEFAULT))

inline fun String.html(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}