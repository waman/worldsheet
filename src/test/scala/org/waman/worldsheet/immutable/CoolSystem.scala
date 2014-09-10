package org.waman.worldsheet.immutable

case class CoolSystem(t:BigDecimal, T:Double)
    extends PhysicalSystem[CoolSystem]{

  val dt = BigDecimal(0.1)
  val T_room = 17.0

  override def next() = CoolSystem(t + dt, T - 0.03 * (T - T_room) * dt.doubleValue())
}
