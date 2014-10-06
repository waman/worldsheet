package org.waman.worldsheet.newtoncooling

import org.waman.worldsheet.SimulationUtil.dec1

object NewtonCoolingPhysicalProcess extends App{

  val system = new NewtonCoolingSystem()
  val stream = system.createPhysicalProcess(82.3, dec1(0.1), 17.0)

  stream.takeWhile(_.temper >= 50.0).foreach(println)
}
