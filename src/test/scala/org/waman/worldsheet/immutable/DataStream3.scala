package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

object DataStream3 extends App{

  val system = RadioActiveSystem(dec(0.0), 1.0e6)
  val mapper = RadioActiveMapper(dec(0.1), Math.log(2)/5.0)
  val observer = (s:RadioActiveSystem) => RadioActiveData(s.t, s.n)

  val stream = DataStream(system, mapper, observer)
  stream.takeWhile(_.numberOfNuclei > 1.25e5).foreach(println)
}
