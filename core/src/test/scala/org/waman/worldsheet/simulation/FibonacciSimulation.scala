package org.waman.worldsheet.simulation

import org.waman.worldsheet.system.SystemNoParam
import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem}

case class FibonacciState(current: Int, next: Int)

class FibonacciSystem extends PhysicalSystem with SystemNoParam{

  type State = FibonacciState

  override val initialState = FibonacciState(0, 1)

  override val stateEvolver = (s:State) => FibonacciState(s.next, s.current + s.next)
}

abstract class AbstractFibonacciSimulation extends PhysicalSimulation with NoParam{

  override type State = FibonacciState
  override type Data = Int

  override val physicalSystem = new FibonacciSystem
  override val observer = (s:State) => s.current
}