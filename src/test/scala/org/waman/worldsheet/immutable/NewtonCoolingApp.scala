package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

object NewtonCoolingApp extends App{

  val sim = new NewtonCoolingSimulation
  val stream = sim.createDataStream(82.3, dec(0.1), 17.0)
  stream.takeWhile(_.temperature > 50.0).foreach(println)
}
