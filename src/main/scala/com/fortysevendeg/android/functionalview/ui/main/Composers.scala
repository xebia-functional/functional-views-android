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
import android.support.v7.widget.{GridLayoutManager, RecyclerView}
import android.view.View
import com.fortysevendeg.android.functionalview._
import com.fortysevendeg.android.functionalview.ui.commons.AsyncImageTweaks._
import com.fortysevendeg.macroid.extras.ImageViewTweaks._
import com.fortysevendeg.macroid.extras.RecyclerViewTweaks._
import com.fortysevendeg.macroid.extras.ResourcesExtras._
import com.fortysevendeg.macroid.extras.TextTweaks._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import macroid.FullDsl._
import macroid.{ActivityContextWrapper, ContextWrapper, Ui}

import scala.language.postfixOps

trait MainComposer {

  self: TypedFindView =>

  lazy val content = Option(findView(TR.content))

  lazy val toolBar = Option(findView(TR.toolbar))

  lazy val appBarLayout = Option(findView(TR.app_bar_layout))

  lazy val recycler = Option(findView(TR.recycler))

  lazy val fabActionButton = Option(findView(TR.fab_action_button))

  def composition(items: Seq[Item])(implicit contextWrapper: ActivityContextWrapper): Ui[_] =
    initRecycler(items) ~ initFabButton

  private[this] def initRecycler(items: Seq[Item])(implicit contextWrapper: ActivityContextWrapper): Ui[_] =
    (recycler
        <~ rvAdapter(new ImageListAdapter(items))
        <~ rvFixedSize
        <~ rvLayoutManager(new GridLayoutManager(contextWrapper.bestAvailable, 2)))

  private[this] def initFabButton(implicit contextWrapper: ContextWrapper): Ui[_] =
    (fabActionButton
        <~ ivSrc(R.drawable.ic_add)
        <~ On.click {
      Ui {
        content foreach (Snackbar.make(_, resGetString(R.string.add_item), Snackbar.LENGTH_LONG).show())
      }
    })


}

case class ImageViewHolder(parent: View)
    extends RecyclerView.ViewHolder(parent)
    with TypedFindView {

  lazy val image = Option(findView(TR.image))
  lazy val text = Option(findView(TR.text))

  override protected def findViewById(id: Int): View = parent.findViewById(id)

  def bind(item: Item, position: Int)(implicit contextWrapper: ActivityContextWrapper): Ui[_] =
    (parent <~ vTag(position.toString)) ~
        (image <~ srcImage(item.url)) ~
        (text <~ tvText(item.category))

}
