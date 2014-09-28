package org.waman.worldsheet

trait StateMapper[S] extends (S => S)
