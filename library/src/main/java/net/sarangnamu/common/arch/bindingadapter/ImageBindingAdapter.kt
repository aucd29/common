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
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import net.sarangnamu.common.R
import net.sarangnamu.common.isVideoPath
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Created by <a href="mailto:aucd29@gmail.com">Burke Choi</a> on 2018. 5. 11.. <p/>
 */

class ImageBindingAdapter {
    companion object {
        private val log = LoggerFactory.getLogger(ImageBindingAdapter::class.java)

        @BindingAdapter("android:src")
        fun imageSrc(view: ImageView, @DrawableRes resid: Int) {
            if (log.isDebugEnabled) {
                log.debug("BIND IMAGE : $resid")
            }

            view.setImageResource(resid)
        }

        @BindingAdapter("android:src")
        fun imagePath(view: ImageView, imagePath: String) {
            if (log.isDebugEnabled) {
                log.debug("BIND IMAGE : $imagePath")
            }

            val context = view.context
            val fp = File(imagePath)

            if (!fp.exists()) {
                log.error("ERROR: FILE NOT FOUND $imagePath")

                // TODO, SET DEFAULT IMAGE
                return
            }

            if (fp.isVideoPath()) {
                val picasso = Picasso.Builder(context).addRequestHandler(VideoRequestHandler()).build()

                picasso.load("${VideoRequestHandler.VIDEO_SCHEME}:${imagePath}")
                    .placeholder(R.drawable.ic_autorenew_black_24dp)
                    .into(view)
            } else {
                Picasso.with(context).load(fp).placeholder(R.drawable.ic_autorenew_black_24dp)
                    .into(view)
            }
        }
    }
}

class VideoRequestHandler : RequestHandler() {
    companion object {
        val VIDEO_SCHEME = "video"
    }

    override fun canHandleRequest(data: Request): Boolean {
        return VIDEO_SCHEME == data.uri.scheme
    }

    override fun load(request: Request, networkPolicy: Int): Result {
        val bm = ThumbnailUtils.createVideoThumbnail(request.uri.path,
                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND)

        return RequestHandler.Result(bm, Picasso.LoadedFrom.DISK)
    }
}