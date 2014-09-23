package org.waman.worldsheet.immutable

object NewtonCoolingApp extends App{

  val sim = new NewtonCoolingSimulation()
  val stream = sim.createDataStream()
  stream.takeWhile(_.temperature > 50.0).foreach(println)
}
