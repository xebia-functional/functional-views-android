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

trait DataService {

  val categories = Seq(
    "animals",
    "city",
    "food",
    "people",
    "sports",
    "technics")

  def getData = categories flatMap { category =>
    1 to 5 map { i =>
     Item(category, s"http://lorempixel.com/500/500/$category/$i")
    }
  }

}

case class Item(category: String, url: String)
