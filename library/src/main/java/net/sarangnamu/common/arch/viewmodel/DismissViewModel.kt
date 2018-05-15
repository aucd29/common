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


package net.sarangnamu.common.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

open class DismissViewModel: ViewModel() {
    protected lateinit var dismiss: MutableLiveData<Boolean>

    open fun init() {
        dismiss = MutableLiveData()
    }

    open fun positiveClose() {
        dismiss.value = true
    }

    open fun negativeClose() {
        dismiss.value = false
    }
}

open class DismissAndroidViewModel(app: Application) : AndroidViewModel(app) {
    protected lateinit var dismiss: MutableLiveData<Boolean>

    open fun init() {
        dismiss = MutableLiveData()
    }

    open fun positiveClose() {
        dismiss.value = true
    }

    open fun negativeClose() {
        dismiss.value = false
    }
}