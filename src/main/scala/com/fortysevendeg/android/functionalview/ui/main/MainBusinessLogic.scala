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

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

trait MainBusinessLogic {

  import MainBusinessLogic._

  def fetchAsyncData: Future[Seq[Item]] = Future {
    Thread.sleep(1000)
    categories flatMap { category =>
      1 to 5 map { i =>
        Item(category, i, selected = false)
      }
    }
  }

}

object MainBusinessLogic {
  val animals = "animals"
  val city = "city"
  val food = "food"
  val people = "people"
  val sports = "sports"
  val technics = "technics"
  val nature = "nature"

  val categories = Seq(animals, city, food, people, sports, technics)

}

