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

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.{GridLayoutManager, RecyclerView}
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.fortysevendeg.android.functionalview._
import com.fortysevendeg.android.functionalview.ui.ImplicitContext
import macroid.FullDsl._
import macroid.{Tweak, Ui}

import scala.language.postfixOps

trait MainPresentationLogic {

  self: MainView =>

  def initUi(items: Seq[Item])(implicit context: ImplicitContext): Ui[_] =
    initRecycler(items) ~ initFabButton

  private[this] def initRecycler(items: Seq[Item])(implicit context: ImplicitContext): Ui[_] =
    recycler <~ Transformations.recycler(items)

  private[this] def initFabButton(implicit context: ImplicitContext): Ui[_] =
    fabActionButton <~ Transformations.actionButton

}

trait MainView {

  val recycler: Option[RecyclerView]

  val fabActionButton: Option[FloatingActionButton]

}

object Transformations {

  def recycler(items: Seq[Item])(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.setAdapter(new ImageListAdapter(items))
      recycler.setHasFixedSize(true)
      recycler.setLayoutManager(new GridLayoutManager(context.bestAvailable, 2))
  }

  def actionButton(implicit context: ImplicitContext) = Tweak[FloatingActionButton] {
    fab =>
      fab.setImageResource(R.drawable.ic_add)
      fab.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit =
          Toast.makeText(context.application, R.string.add_item, Toast.LENGTH_LONG).show()
      })
  }

}
