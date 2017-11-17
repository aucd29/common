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

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import java.lang.ref.WeakReference

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 9. 28.. <p/>
 */
open class BkApp : Application() {
    companion object {
        lateinit var weak: WeakReference<Context>
        lateinit var screen: Point

        fun screenX() = screen.x
        fun screenY() = screen.y
        fun context(): Context = weak.get()!!
    }

    override fun onCreate() {
        super.onCreate()

        weak = WeakReference(applicationContext)

        val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getSize(screen)
    }
}