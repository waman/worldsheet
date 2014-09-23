package org.waman.worldsheet.immutable

trait StateMapper[S] extends (S => S)
