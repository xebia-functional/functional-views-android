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

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fortysevendeg.android.functionalview.{R, TR, TypedFindView}
import macroid.Contexts
import macroid.FullDsl._
import scala.concurrent.ExecutionContext.Implicits.global

class MainActivity
  extends AppCompatActivity
  with TypedFindView
  with MainView
  with MainPresentationLogic
  with MainBusinessLogic
  with Contexts[Activity] {

  lazy val recycler = Option(findView(TR.recycler))

  lazy val fabActionButton = Option(findView(TR.fab_action_button))

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    val maybeToolBar = Option(findView(TR.toolbar))
    maybeToolBar map { toolbar =>
      setSupportActionBar(toolbar)
    }

    fetchAsyncData map { data =>
      runUi(initUi(data))
    }
  }


}
