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

import android.arch.lifecycle.MutableLiveData
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 3.. <p/>
 */

open abstract class VmAdapter<I : IItem,
                              VM : VmViewModel<MutableLiveData<List<Any>>>,
                              VH : VmViewHolder<IItem, ViewDataBinding>>
    : RecyclerView.Adapter<VH> {

    protected var mDataList: List<I>? = null
    protected var mModel: VM

    constructor(viewModel: VM) {
        this.mModel    = viewModel
        this.mDataList = viewModel.dataList.value as List<I>
    }

    protected fun <T: ViewDataBinding> inflate(@NonNull parent: ViewGroup, @LayoutRes resid: Int): T {
        return parent.context.inflateDataBinding(resid, parent, false)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = mDataList?.run { get(position) } ?: null
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return mDataList?.run { size } ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return mDataList?.run { get(position).type() } ?: 0
    }

    fun invalidate(dataList: List<I>, all: Boolean) {
        if (all) {
            this.mDataList = dataList
            notifyDataSetChanged()

            return
        }

        val len = mDataList?.size ?: 0
        mDataList = dataList

        notifyItemRangeChanged(len, dataList.size)
    }
}