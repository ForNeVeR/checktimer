// SPDX-FileCopyrightText: 2024 Friedrich von Never <friedrich@fornever.me>
//
// SPDX-License-Identifier: MIT

package me.fornever.checktimer.services

class StubWindowService extends WindowService {
  override def stayOnTop(state: Boolean): Unit = {}
}
