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

import android.app.Activity
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 3.. <p/>
 */

fun <T : ViewDataBinding> Activity.inflateDataBinding(@LayoutRes layoutId: Int, parent: ViewGroup?, attachToParent: Boolean): T {
    val inflater = LayoutInflater.from(this)

    return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent)
}

fun <T : ViewDataBinding> Fragment.inflateDataBinding(@LayoutRes layoutId: Int, parent: ViewGroup?, attachToParent: Boolean): T {
    val inflater = LayoutInflater.from(this.activity)

    return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent)
}

fun <T : ViewDataBinding> Context.inflateDataBinding(@LayoutRes layoutId: Int, parent: ViewGroup?, attachToParent: Boolean): T {
    val inflater = LayoutInflater.from(this)

    return DataBindingUtil.inflate(inflater, layoutId, parent, attachToParent)
}

fun <T : ViewDataBinding> Activity.contentViewDataBinding(@LayoutRes layoutId: Int): T {
    return DataBindingUtil.setContentView(this, layoutId)
}

