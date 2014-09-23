package org.waman.worldsheet.immutable

object RadioActivePhysicalProcess extends App{

  val system = new RadioActiveSystem(1.0e6, Math.log(2)/5.0)
  val process = system.createPhysicalProcess()

  process.takeWhile(_.n >= 1.25e5).foreach(println)
}
