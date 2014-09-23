package org.waman.worldsheet.immutable

object RadioActiveApp extends App{

  val sim = new RadioActiveSimulation()
  val stream = sim.createDataStream()
  stream.takeWhile(_.numberOfNuclei > 1.25e5).foreach(println)
}
