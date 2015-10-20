/*
 *  Copyright (C) 2015 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortysevendeg.android.functionalview.ui.commons

import android.widget.ImageView
import com.fortysevendeg.android.functionalview.ui._
import com.squareup.picasso.Picasso
import macroid.Tweak

import scala.language.postfixOps

object AsyncImageTweaks {
  type W = ImageView

  def srcImage(
    url: String,
    placeHolder: Int
  )(implicit contextWrapper: ImplicitContext): Tweak[W] = Tweak[W](
    imageView =>
      Picasso.`with`(contextWrapper.application)
        .load(url)
        .placeholder(placeHolder)
        .into(imageView)
  )

  def srcImage(url: String)(implicit contextWrapper: ImplicitContext): Tweak[W] = Tweak[W](
    imageView =>
      Picasso.`with`(contextWrapper.application)
        .load(url)
        .into(imageView)
  )
}

