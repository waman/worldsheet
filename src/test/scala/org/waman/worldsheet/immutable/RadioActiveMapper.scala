package org.waman.worldsheet.immutable

case class RadioActiveMapper(dt:BigDecimal, lambda:Double)
    extends ((RadioActiveSystem) => RadioActiveSystem){

  override def apply(system: RadioActiveSystem): RadioActiveSystem =
    RadioActiveSystem(system.t + dt, system.n - lambda * system.n * dt.doubleValue())
}
