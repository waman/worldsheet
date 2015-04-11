package org.waman.worldsheet.simulation

import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem}

case class FibonacciState(no: Int, current: Int, next: Int)

class FibonacciSystem extends PhysicalSystem{

  type State = FibonacciState
  type Params = (Int, Int)

  protected def newInitialState(params:Params) = FibonacciState(0, params._1, params._2)

  protected def newStateEvolver(params:Params) = s => FibonacciState(s.no + 1, s.next, s.current + s.next)
}

abstract class AbstractFibonacciSimulation extends PhysicalSimulation{

  type State = FibonacciState
  type Params = (Int, Int)

  val physicalSystem = new FibonacciSystem
}