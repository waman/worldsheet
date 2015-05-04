package org.waman.worldsheet.sample.newtoncooling

import spire.syntax.literals._

object NewtonCoolingApp extends App{

  val sim = new NewtonCoolingSimulation
  val stream = sim.newDataSeq(82.3, r"0.1", 17.0)
  stream.takeWhile(_.temperature > 50.0).foreach(println)
}
