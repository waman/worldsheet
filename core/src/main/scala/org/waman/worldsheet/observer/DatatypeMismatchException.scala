package org.waman.worldsheet.observer

import org.waman.worldsheet.SimulationUtil._

class DatatypeMismatchException(mismatches:Set[String],
                                dataInfo:Map[String, Class[_]],
                                result:Map[String, Any]) extends ObservationException(null, null){

  require(mismatches.forall(dataInfo.contains))
  require(mismatches.forall(result.contains))

  override lazy val getMessage:String = {
    val message = mismatches.map(name =>
      sep + "    \""+name+"\" must be "+dataInfo.get(name).get.getName+
        ", but actual be "+classAndValue(result.get(name).get)
    ).mkString

    "Observable type mismatch:" + sep +
      "    " + quotedJoin(mismatches) + " are invalid datatype" +
      message
  }
}
