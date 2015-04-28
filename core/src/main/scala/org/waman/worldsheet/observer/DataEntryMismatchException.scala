package org.waman.worldsheet.observer

import org.waman.worldsheet.SimulationUtil._

class DataEntryMismatchException(expected:Set[String], actual:Set[String])
  extends ObservationException(null, null){

  override lazy val getMessage: String = {
    val message = new StringBuilder

    val tooMuch = actual -- expected
    if (tooMuch.nonEmpty)
      message ++= (sep + "    " + quotedJoin(tooMuch) + " must not be observed")

    val tooLittle = expected -- actual
    if (tooLittle.nonEmpty)
      message ++= (sep + "    " + quotedJoin(tooLittle) + " must be observed")

    "Observable mismatch:" + message
  }
}
