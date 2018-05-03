/*
 * Copyright (C) Hanwha S&C Ltd., 2018. All rights reserved.
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

package net.sarangnamu.common.binding

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.app.FragmentActivity
import java.lang.ref.WeakReference

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 3.. <p/>
 */

open class ViewModelBase(app: Application) : AndroidViewModel(app) {
    var weakActivity: WeakReference<FragmentActivity>? = null

    fun setActivity(activity: FragmentActivity) {
        weakActivity = WeakReference(activity)
    }

    fun getActivity() : FragmentActivity? {
        return weakActivity?.get()
    }
}

