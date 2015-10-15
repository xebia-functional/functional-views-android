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

package com.fortysevendeg.android.functionalview.ui.main

import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import com.fortysevendeg.android.functionalview.R
import macroid.ActivityContextWrapper
import macroid.FullDsl._

case class ImageListAdapter(items: Seq[Item])(implicit context: ActivityContextWrapper)
    extends RecyclerView.Adapter[ImageViewHolder]
    with View.OnClickListener {

  override def onCreateViewHolder(parent: ViewGroup, i: Int): ImageViewHolder = {
    val v = LayoutInflater.from(parent.getContext).inflate(R.layout.image_item, parent, false)
    v.setOnClickListener(this)
    new ImageViewHolder(v)
  }

  override def getItemCount: Int = items.size

  override def onBindViewHolder(viewHolder: ImageViewHolder, position: Int): Unit =
    runUi(viewHolder.bind(items(position), position))

  override def onClick(v: View): Unit = {
    val item = items(v.getTag.toString.toInt)
    Snackbar.make(v, item.category, Snackbar.LENGTH_LONG).show()
  }
}


