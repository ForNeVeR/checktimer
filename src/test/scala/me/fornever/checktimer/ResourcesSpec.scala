// SPDX-FileCopyrightText: 2026 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ResourcesSpec extends AnyFlatSpec with Matchers {

  "Resources" should "load pinIcon successfully" in {
    val icon = Resources.pinIcon
    icon should not be null
  }
}
