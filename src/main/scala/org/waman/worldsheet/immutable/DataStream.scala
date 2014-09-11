package org.waman.worldsheet.immutable

object DataStream {

  def apply[S <: PhysicalSystem[S], D](system: S, observer: S => D): Stream[D] =
    SystemStream(system).map(observer)

  def apply[S, D](system: S, mapper: S => S, observer: S => D): Stream[D] =
    SystemStream(system, mapper).map(observer)
}
