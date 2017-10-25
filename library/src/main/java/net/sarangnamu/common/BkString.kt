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

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 10. 16.. <p/>
 */

public fun CharSequence.isLastSlash(): Boolean =
        !isEmpty() && lastIndexOf('/') != -1

public fun String.addLastSlash(): String {
    if (isEmpty()) {
        return this
    }

    if (!endsWith("/")) {
        return this + "/"
    }

    return this
}

public fun String.fileName(): String {
    return substringBeforeLast('/', "")
}

public fun String.fileExtension(): String {
    return substringAfterLast('.', "")
}
