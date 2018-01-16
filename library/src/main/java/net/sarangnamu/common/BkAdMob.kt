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

package net.sarangnamu.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2017. 11. 30.. <p/>
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