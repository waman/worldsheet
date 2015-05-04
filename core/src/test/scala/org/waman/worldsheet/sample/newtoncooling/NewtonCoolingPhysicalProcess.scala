package org.waman.worldsheet.sample.newtoncooling

import spire.syntax.literals._

object NewtonCoolingPhysicalProcess extends App{

  val system = new NewtonCoolingSystem
  val pp = system.newPhysicalProcess(82.3, r"0.1", 17.0)

  pp.takeWhile(_.temper >= 50.0).foreach(println)
}
