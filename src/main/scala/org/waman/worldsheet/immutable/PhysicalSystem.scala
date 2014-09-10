package org.waman.worldsheet.immutable

trait PhysicalSystem[S <: PhysicalSystem[S]] {

  def next():S
}
