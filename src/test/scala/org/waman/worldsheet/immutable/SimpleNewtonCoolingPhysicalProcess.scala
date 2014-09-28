package org.waman.worldsheet.immutable

import org.waman.worldsheet.Util.dec

class SimpleNewtonCoolingSystem extends SimplePhysicalSystem{

  override type State = NewtonCoolingState

  override val initialState = NewtonCoolingState(dec(0.0), 82.3)
  override val stateMapper = (s:State) => new State(
    s.time + dec(0.1),
    s.temper - 0.03 * (s.temper - 17.0) * 0.1
  )
}

object SimpleNewtonCoolingPhysicalProcess extends App{
  val system = new SimpleNewtonCoolingSystem
  val stream = system.createPhysicalProcess()
  stream.take(20).foreach(println)
}