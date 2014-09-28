package org.waman.worldsheet.newtoncooling

import org.waman.worldsheet.SimulationUtil
import SimulationUtil.dec1

object NewtonCoolingApp extends App{

  val sim = new NewtonCoolingSimulation
  val stream = sim.createDataStream(82.3, dec1(0.1), 17.0)
  stream.takeWhile(_.temperature > 50.0).foreach(println)
}
