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

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import net.sarangnamu.common.arch.adapter.BkAdapter

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

open class RecyclerViewModel<T> : ViewModel() {
    val items   = ObservableArrayList<T>()
    val adapter = ObservableField<BkAdapter<T>>()

    open fun initAdapter(id: String) {
        val adapter = BkAdapter<T>(id)
        adapter.items  = this.items
        adapter.vmodel = this

        this.adapter.set(adapter)
    }

    open fun initAdapter(ids: Array<String>) {
        val adapter = BkAdapter<T>(ids)
        adapter.items  = this.items
        adapter.vmodel = this

        this.adapter.set(adapter)
    }

    fun clearItems(items: ArrayList<T>) {
        this.items.clear()
        this.items.addAll(items)
    }
}