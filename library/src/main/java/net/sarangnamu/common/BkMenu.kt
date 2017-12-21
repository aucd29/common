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
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 12. 21.. <p/>
 */

class MenuManager {
    private object Holder { val INSTANCE = MenuManager() }

    companion object {
        val get: MenuManager by lazy { Holder.INSTANCE }
    }

    private lateinit var popup: PopupMenu

    fun show(context: Context, v: View, resid: Int, listener: (MenuItem?) -> Boolean) {
        popup = PopupMenu(context, v)
        popup.menuInflater.inflate(resid, popup.menu)
        popup.setOnMenuItemClickListener(listener)
        popup.show()
    }
}