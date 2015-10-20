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

import android.support.v7.widget.RecyclerView
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.{ImageView, TextView}
import com.fortysevendeg.android.functionalview.ui._
import com.fortysevendeg.android.functionalview.ui.commons.AsyncImageTweaks._
import com.fortysevendeg.android.functionalview.{R, TR, TypedFindView}
import macroid.FullDsl._
import macroid.Ui

case class ImageListAdapter(items: Seq[Item], clickListener: Int => Ui[_])(implicit context: ImplicitContext)
  extends RecyclerView.Adapter[ImageViewHolder] {

  override def onCreateViewHolder(parent: ViewGroup, i: Int): ImageViewHolder = {
    val v = LayoutInflater.from(parent.getContext).inflate(R.layout.image_item, parent, false)
    ImageViewHolder(v)
  }

  override def getItemCount: Int = items.size

  override def onBindViewHolder(viewHolder: ImageViewHolder, position: Int): Unit =
    runUi(viewHolder.bind(items(position), position, clickListener))

}

trait AdapterItemView {

  val parent: View

  val image: Option[ImageView]

  val description: Option[TextView]

  val check: Option[ImageView]

}

trait AdapterItemPresentationLogic {

  self: AdapterItemView =>

  def bind(item: Item, position: Int, clickListener: Int => Ui[_])(implicit context: ImplicitContext): Ui[_] =
    (parent <~ On.click(clickListener(position))) ~
      (image <~ srcImage(item.url)) ~
      (description <~ text(item.category)) ~
      (check <~ (if (item.selected) show else hide))

}

case class ImageViewHolder(parent: View)
  extends RecyclerView.ViewHolder(parent)
  with TypedFindView
  with AdapterItemView
  with AdapterItemPresentationLogic {

  lazy val image = Option(findView(TR.image))
  lazy val description = Option(findView(TR.text))
  lazy val check = Option(findView(TR.check))

  override protected def findViewById(id: Int): View = parent.findViewById(id)

}
