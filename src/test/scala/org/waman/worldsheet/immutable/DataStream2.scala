package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

object DataStream2 extends App{

  val system = NewtonCoolingSystem(dec(0.0), 82.3)
  val observer = (s: NewtonCoolingSystem) => NewtonCoolingData(s.t, s.T)

  val stream = DataStream(system, observer)
  stream.takeWhile(_.temperature > 50.0).foreach(println)
}
