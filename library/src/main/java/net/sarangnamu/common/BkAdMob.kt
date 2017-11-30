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

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 30.. <p/>
 */

class AdMob {
    private object Holder { val INSTANCE = AdMob() }

    companion object {
        val get: AdMob by lazy { Holder.INSTANCE }
    }

    var adView: AdView? = null
    var interstitial: InterstitialAd? = null

    fun init(context: Context, adId: String, testDeviceId: String) {
        interstitial = InterstitialAd(context)
        interstitial?.run {
            adUnitId = adId
            loadAd(AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice(testDeviceId)
                    .build())
        }
    }

    fun load(activity: Activity, id: Int) {
        adView = activity.findViewById(id)
        loadAd()
    }

    fun load(dialog: Dialog, id: Int) {
        adView = dialog.findViewById(id)
        loadAd()
    }

    fun load(view: View, id: Int) {
        adView = view.findViewById(id)
        loadAd()
    }

    fun destroy() {
        adView?.destroy()
    }

    private fun loadAd() {
        adView?.loadAd(AdRequest.Builder().build())
    }
}