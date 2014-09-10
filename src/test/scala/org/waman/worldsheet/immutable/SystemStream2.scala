package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

object SystemStream2 extends App{

  val system = RadioActiveSystem(dec(0.0), 1.0e6)
  val mapper = RadioActiveMapper(dec(0.1), Math.log(2.0)/5.0)

  val stream = SystemStream(system, mapper)

  stream.takeWhile(_.n >= 1.25e5).foreach(println)
}
