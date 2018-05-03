/*
 * Copyright 2018 Burke Choi All rights reserved.
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

