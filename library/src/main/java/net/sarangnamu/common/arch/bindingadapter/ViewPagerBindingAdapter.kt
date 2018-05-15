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
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import org.slf4j.LoggerFactory

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

class ViewPagerBindingAdapter {
    companion object {
        private val log = LoggerFactory.getLogger(ViewPagerBindingAdapter::class.java)
        
        @BindingAdapter("bindOffscreenPageLimit")
        fun bindOffscreenPageLimit(viewpager: ViewPager, limit: Int) {
            viewpager.offscreenPageLimit = limit
        }

        @BindingAdapter("bindAdapter")
        fun bindAdapter(viewpager: ViewPager, adapter: FragmentPagerAdapter) {
            if (log.isTraceEnabled()) {
                log.trace("BIND ADAPTER $adapter")
            }

            viewpager.adapter = adapter
        }

        @BindingAdapter("bindPageChangeListener")
        fun bindPageChangeListener(viewpager: ViewPager, listener: ViewPager.OnPageChangeListener) {
            if (log.isTraceEnabled()) {
                log.trace("BIND PAGE CHANGE LISTENER")
            }

            viewpager.addOnPageChangeListener(listener)
        }
    }
}