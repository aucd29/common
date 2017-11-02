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

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

/**
 * 문자열 끝에 / 이 존재하는지 확인
 */
fun CharSequence.isLastSlash(): Boolean =
        !isEmpty() && lastIndexOf(File.separator) != -1

/**
 * 문자열 끝에 / 를 붙임
 */
fun String.addLastSlash(): String {
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
fun String.fileFullName(): String = substringAfterLast('/')

/**
 * 파일 경로만 반환
 */
fun String.filePath(): String = substringBeforeLast('/', "")

/**
 * 파일이름만 반환
 */
fun String.fileName(): String = substringAfterLast('/')

/**
 * 파일 확장자만 반환
 */
fun String.fileExtension(): String = fileFullName().substringBeforeLast('.')
