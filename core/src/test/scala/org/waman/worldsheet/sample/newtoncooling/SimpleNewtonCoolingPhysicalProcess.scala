package org.waman.worldsheet.sample.newtoncooling

import org.waman.worldsheet.PhysicalSystem
import org.waman.worldsheet.system.SystemNoParam
import spire.syntax.literals._

class SimpleNewtonCoolingSystem extends PhysicalSystem with SystemNoParam{

  override type State = NewtonCoolingState

  override val initialState = NewtonCoolingState(r"0.1", 82.3)
  override val stateEvolver = (s:State) => new State(
    s.time + r"0.1",
    s.temper - 0.03 * (s.temper - 17.0) * 0.1
  )
}

object SimpleNewtonCoolingPhysicalProcess extends App{
  val system = new SimpleNewtonCoolingSystem
  val stream = system.newPhysicalProcess()
  stream.take(20).foreach(println)
}