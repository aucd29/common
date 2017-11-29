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

@file:Suppress("NOTHING_TO_INLINE", "unused")
package net.sarangnamu.common

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.lang.reflect.ParameterizedType

/**
 * Created by <a href="mailto:aucd29@hanwha.com">Burke Choi</a> on 2017. 11. 28.. <p/>
 *
 * ```kotlin
 * **example**
 *
 *  val dataList = arrayListOf("test1", "test2", "test3", "test4")
    adapter = object: V7Adapter<String, ViewHolder>(this, R.layout.main_item, dataList) {
        override fun bindData(holder: ViewHolder, data: String) {
            holder.a.text = data
        }
    }

    adapter.clickListener = { v, p ->
        Log.e("TAG", "POSITION: $p")
    }

    recyclerView.verticalLayout()
    recyclerView.adapter = adapter
 * ```
 * ```kotlin
 * **update data**
 * val dataList = arrayListOf("test1", "test2", "test3", "test4")
 * adapter.invalidate(dataList)
 * ```
 * ```kotlin
 * **update data**
 * adapter.invalidate(1, "1번째 데이터 갱신")
 * ```
 */

abstract class V7Adapter<T, H: RecyclerView.ViewHolder>(
        var context: Context,
        @LayoutRes var id: Int,
        var dataList: ArrayList<T>) : RecyclerView.Adapter<H>() {

    var clickListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): H {
        val klass       = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<H>
        val constructor = klass.getDeclaredConstructor(*arrayOf<Class<*>>(View::class.java))
        val view        = LayoutInflater.from(context).inflate(id, parent,false)
        val viewHolder  = constructor.newInstance(*arrayOf(view))

        clickListener?.let { view.setOnClickListener { v -> it(v, viewHolder.layoutPosition) } }

        return viewHolder
    }

    override fun getItemCount(): Int = dataList.size
    override fun onBindViewHolder(holder: H, position: Int) = bindData(holder, dataList.get(position))

    fun invalidate(dataList: ArrayList<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    fun invalidate(pos: Int, data: T) {
        this.dataList.set(pos, data)
        notifyItemChanged(pos)
    }

    abstract fun bindData(holder: H, data: T)
}

inline fun RecyclerView.verticalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
}

inline fun RecyclerView.horizontalLayout() {
    layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
}
