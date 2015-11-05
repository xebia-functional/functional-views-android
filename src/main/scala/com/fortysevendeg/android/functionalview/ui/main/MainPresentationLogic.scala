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
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener
import android.support.v7.widget.{GridLayoutManager, PopupMenu, RecyclerView}
import android.view.View.OnClickListener
import android.view.{MenuItem, View}
import android.widget.Toast
import com.fortysevendeg.android.functionalview._
import com.fortysevendeg.android.functionalview.ui.ImplicitContext
import com.fortysevendeg.android.functionalview.ui.main.MainBusinessLogic._
import macroid.FullDsl._
import macroid.{Tweak, Ui}

import scala.language.postfixOps

trait MainPresentationLogic {

  val recycler: Option[RecyclerView]

  val fabActionButton: Option[FloatingActionButton]

  def initUi(items: Seq[Item])(implicit context: ImplicitContext): Ui[_] =
    initRecycler(items) ~ initFabButton

  private[this] def initRecycler(items: Seq[Item])(implicit context: ImplicitContext): Ui[_] =
    recycler <~ Transformations.recycler(items, onClickItemListener)

  private[this] def initFabButton(implicit context: ImplicitContext): Ui[_] =
    fabActionButton <~ Transformations.actionButton(onClickFabListener)

  private[this] def onClickItemListener(position: Int)(implicit context: ImplicitContext): Ui[_] =
    recycler <~ Transformations.recyclerSwapAdapter(position)

  private[this] def onClickFabListener()(implicit context: ImplicitContext): Ui[_] =
    fabActionButton <~ Transformations.showPopupMenu(onClickMenuListener)

  private[this] def onClickMenuListener(menuItem: Int)(implicit context: ImplicitContext): Ui[_] =
    menuItem match {
      case R.id.useCase1 => recycler <~ Transformations.useCase1
      case R.id.useCase2 => recycler <~ Transformations.useCase2(Seq(animals, nature, food))
      case R.id.useCase3 => recycler <~ Transformations.useCase3
      case R.id.useCase4 => recycler <~ Transformations.useCase4
      case _ => Ui.nop
    }

}

object Transformations {

  def recycler(items: Seq[Item], clickListener: Int => Ui[_])(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.setAdapter(new ImageListAdapter(items, clickListener))
      recycler.setHasFixedSize(true)
      recycler.setLayoutManager(new GridLayoutManager(context.bestAvailable, 2))
  }

  def actionButton(clickListener: () => Ui[_])(implicit context: ImplicitContext) = Tweak[FloatingActionButton] {
    fab =>
      fab.setImageResource(R.drawable.ic_add)
      fab.setOnClickListener(new OnClickListener {
        override def onClick(v: View): Unit = runUi(clickListener())
      })
  }

  def recyclerSwapAdapter(position: Int)(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.getAdapter match {
        case a: ImageListAdapter =>
          val items = a.items.zipWithIndex map {
            case (item, pos) => if (pos == position) item.copy(selected = !item.selected) else item
          }
          recycler.swapAdapter(a.copy(items = items), false)
        case _ =>
          Toast.makeText(context.application, R.string.error, Toast.LENGTH_SHORT).show()
      }
  }

  def showPopupMenu(clickMenuListener: (Int) => Ui[_])(implicit context: ImplicitContext) = Tweak[FloatingActionButton] {
    fab =>
      val popup = new PopupMenu(context.bestAvailable, fab)
      popup.inflate(R.menu.use_cases)
      popup.setOnMenuItemClickListener(new OnMenuItemClickListener {
        override def onMenuItemClick(menuItem: MenuItem): Boolean = {
          runUi(clickMenuListener(menuItem.getItemId))
          true
        }
      })
      popup.show()
  }

  def useCase1(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.getAdapter match {
        case a: ImageListAdapter =>
          val animalsConvertedToNature = a.items map {
            case Item(`animals`, position, selected) => Item(nature, position, selected)
            case i => i
          }
          recycler.swapAdapter(a.copy(animalsConvertedToNature), false)
        case _ =>
          Toast.makeText(context.application, R.string.error, Toast.LENGTH_SHORT).show()
      }
  }

  def useCase2(filterCategories: Seq[String])(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.getAdapter match {
        case a: ImageListAdapter =>
          val animalItems = a.items filter { item =>
            filterCategories.contains(item.category)
          }
          recycler.swapAdapter(a.copy(animalItems), false)
        case _ =>
          Toast.makeText(context.application, R.string.error, Toast.LENGTH_SHORT).show()
      }
  }

  def useCase3(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.getAdapter match {
        case a: ImageListAdapter =>
          val animalItems = a.items sortBy(_.category)
          recycler.swapAdapter(a.copy(animalItems), false)
        case _ =>
          Toast.makeText(context.application, R.string.error, Toast.LENGTH_SHORT).show()
      }
  }

  def useCase4(implicit context: ImplicitContext) = Tweak[RecyclerView] {
    recycler =>
      recycler.getAdapter match {
        case a: ImageListAdapter =>
          val count = a.items count (_.selected)
          Toast.makeText(context.application, context.application.getString(R.string.items_selected, count.toString), Toast.LENGTH_SHORT).show()
        case _ =>
          Toast.makeText(context.application, R.string.error, Toast.LENGTH_SHORT).show()
      }
  }

}
