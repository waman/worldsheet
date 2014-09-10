package org.waman.worldsheet.immutable

object SystemStream {

  def apply[S <: PhysicalSystem[S]](system: S): Stream[S] =
    Stream.iterate(system)(_.next())

  def apply[S](system: S, mapper: S => S): Stream[S] =
    Stream.iterate(system)(mapper)
}
