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

package net.sarangnamu.common.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 4.. <p/>
 *
 * 커스텀 다이얼로그를 이용할 때 사용
 *
 * ```kotlin
 * YourViewBinding binding = inflateBinding(R.layout.your_view)
 * DialogViewModel model = ViewModelProviders.of(this).get(DialogViewModel.class)
 *
 * model.init()
 * model.title.set($title)
 * model.message.set($message)
 * model.dismiss.observe(this, result -> dialog.dismiss());
 *
 * binding.setModel(model)
 *
 * ```
 */

class DialogViewModel : ViewModel() {
    var title   = ObservableField<String>()
    var message = ObservableField<String>()
    var dismiss = MutableLiveData<Boolean>()

    fun init() {
        dismiss = MutableLiveData<Boolean>()
    }

    fun positiveClose() {
        dismiss.value = true
    }

    fun negativeClose() {
        dismiss.value = false
    }
}
