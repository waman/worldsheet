package org.waman.worldsheet.simulation

import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem}

case class FibonacciState(no: Int, current: Int, next: Int)

class FibonacciSystem extends PhysicalSystem{

  type State = FibonacciState
  type Param = (Int, Int)

  protected def newInitialState(param: (Int, Int)) = FibonacciState(0, param._1, param._2)

  protected def newStateEvolver(param: (Int, Int)) = s => FibonacciState(s.no + 1, s.next, s.current + s.next)
}

abstract class AbstractFibonacciSimulation extends PhysicalSimulation{

  type State = FibonacciState
  type Param = (Int, Int)

  val physicalSystem = new FibonacciSystem
}