package org.waman.worldsheet.immutable

case class NewtonCoolingSystem(t:BigDecimal, T:Double)
    extends PhysicalSystem[NewtonCoolingSystem]{

  val dt = BigDecimal(0.1)
  val T_room = 17.0

  override def next() = NewtonCoolingSystem(t + dt, T - 0.03 * (T - T_room) * dt.doubleValue())
}
