package org.waman.worldsheet.simulation

import org.scalatest._
import org.waman.worldsheet.{FibonacciState, PhysicalSystem, PhysicalSimulation}

class StateAsDataSimulation extends PhysicalSimulation
    with StateAsData{

  override type State = FibonacciState
  override type Param = (Int, Int)

  override val physicalSystem = new PhysicalSystem{

    override type State = StateAsDataSimulation.this.State
    override type Param = StateAsDataSimulation.this.Param

    override def newInitialState(param: Param) =
      FibonacciState(param._1, param._2)

    override def newStateEvolver(param: Param) =
      (s:State) => FibonacciState(s.next, s.current + s.next)
  }
}

class StateAsDataTest extends FlatSpec with Matchers {

  "A StateAsData" should "be able to define well" in {
    val dataSeq = new StateAsDataSimulation().newDataSeq(1, 2)
    dataSeq.map(_.current).take(5) should be (Seq(1, 2, 3, 5, 8))
  }
}