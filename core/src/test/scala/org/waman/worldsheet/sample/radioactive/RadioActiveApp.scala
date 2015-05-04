package org.waman.worldsheet.sample.radioactive

object RadioActiveApp extends App{

  val sim = new RadioActiveSimulation()
  val stream = sim.newDataSeq()
  stream.takeWhile(_.numberOfNuclei > 1.25e5).foreach(println)
}
