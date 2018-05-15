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

package net.sarangnamu.common.arch.bindingadapter

import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.support.v7.widget.RecyclerView
import net.sarangnamu.common.arch.adapter.BkAdapter
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

class RecyclerBindingAdapter {
    companion object {
        private val log = LoggerFactory.getLogger(RecyclerBindingAdapter::class.java)

        @BindingAdapter("bindAdapter")
        fun bindAdapter(recycler: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            if (log.isDebugEnabled) {
                log.debug("BIND ADAPTER")
            }

            recycler.adapter = adapter
        }

        @BindingAdapter("bindNotifyDataSetChanged")
        fun bindNotifyDataSetChanged(recycler: RecyclerView, items: ObservableArrayList<Any>) {
            if (log.isDebugEnabled) {
                log.debug("BIND ITEMS")
            }

            val adapter = recycler.adapter as BkAdapter<Any>
            if (adapter == null || items == null) {
                return
            }

            adapter.changeItems(items)
            if (adapter.diffCallback == null) {
                adapter.notifyDataSetChanged()
            }
        }

        @BindingAdapter("bindNotifyItemRangeChanged")
        fun bindNotifyItemRangeChanged(recycler: RecyclerView, items: ObservableArrayList<Any>) {
            if (log.isDebugEnabled) {
                log.debug("BIND ITEMS")
            }

            val adapter = recycler.adapter as BkAdapter<Any>
            if (adapter == null || items == null) {
                return
            }

            val old  = adapter.itemCount
            val curr = items.size

            adapter.changeItems(items)
            if (adapter.diffCallback == null) {
                adapter.notifyItemRangeChanged(old, curr)
            }
        }
    }
}