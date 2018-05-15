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


package net.sarangnamu.common.arch.adapter

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

interface IBkItem {
    fun type() : Int
}

interface BkContentDiffCallback {
    fun diffContent(oldItem: Any?, newItem: Any?): Boolean
}

class BkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var binding: ViewDataBinding
}

class BkAdapter<I> : RecyclerView.Adapter<BkViewHolder> {
    companion object {
        private val log = LoggerFactory.getLogger(BkAdapter::class.java)

        protected fun bindClassName(context: Context, layoutId: String): String {
            val sb = StringBuilder()

            sb.append(context.packageName)
            sb.append(".databinding.")
            sb.append(Character.toUpperCase(layoutId[0]))

            var i = 1
            while (i < layoutId.length) {
                var c = layoutId[i]
                if (c == '_') {
                    c = layoutId[++i]
                    sb.append(Character.toUpperCase(c))
                } else {
                    sb.append(c)
                }
            }

            sb.append("Binding")

            return sb.toString()
        }

        protected fun invokeMethod(binding: ViewDataBinding, methodName: String, argType: Class<Any>, argVal: Any, logmsg: Boolean) {
            try {
                val method = binding.javaClass.getDeclaredMethod(methodName, argType)
                method.invoke(binding, argVal)
            } catch (e: Exception) {
                if (logmsg) {
                    if (log.isDebugEnabled) {
                        log.debug("NOT FOUND : ${e.message}")
                    }
                }
            }
        }
    }

    private lateinit var layoutIds: Array<String>

    var items: List<I>? = null
    var vmodel: ViewModel? = null
    var diffCallback: BkContentDiffCallback? = null

    constructor(layoutId : String) {
        layoutIds = arrayOf(layoutId)
    }

    constructor(layoutIds: Array<String>) {
        this.layoutIds = layoutIds
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BkViewHolder? {
        val context  = parent.context
        val layoutId = context.resources.getIdentifier(layoutIds[viewType], "layout", context.packageName)
        val view     = LayoutInflater.from(context).inflate(layoutId, parent, false)
        
        if (log.isTraceEnabled()) {
            log.trace(String.format("LAYOUT ID : %s (%d)", layoutIds[viewType], layoutId))
        }

        try {
            val classPath = bindClassName(context, layoutIds[viewType])

            if (log.isTraceEnabled()) {
                log.trace("BINDING CLASS : $classPath")
            }

            val bindingClass = Class.forName(classPath)
            val method       = bindingClass.getDeclaredMethod("bind", View::class.java)
            val vh           = BkViewHolder(view)
            vh.binding       = method.invoke(null, view) as ViewDataBinding

            return vh
        } catch (e: Exception) {
            e.printStackTrace()
            log.error("ERROR: ${e.message}")

            return null
        }
    }

    override fun onBindViewHolder(holder: BkViewHolder?, position: Int) {
        holder?.let { h ->
            vmodel?.let { invokeMethod(h.binding, "setModel", it.javaClass, it, false) }

            items?.let { invokeMethod(h.binding, "setItem", it.javaClass, it, false) }
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        if (items == null) {
            return 0
        }

        val item = items!!.get(position)
        return if (item is IBkItem) item.type() else 0
    }

    fun changeItems(newItems: ArrayList<I>) {
        if (this.items == null) {
            this.items = newItems
            notifyItemRangeInserted(0, newItems.size)
            return
        }

        if (diffCallback != null) {
            val diffRes = DiffUtil.calculateDiff(object: DiffUtil.Callback() {
                override fun getOldListSize(): Int = items!!.size
                override fun getNewListSize(): Int = newItems.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                        items!!.get(oldItemPosition) == newItems.get(newItemPosition)

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return diffCallback!!.diffContent(items!!.get(oldItemPosition), newItems.get(newItemPosition))
                }
            })

            this.items = newItems
            diffRes.dispatchUpdatesTo(this)
        } else {
            this.items = newItems
        }
    }
}
