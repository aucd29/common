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