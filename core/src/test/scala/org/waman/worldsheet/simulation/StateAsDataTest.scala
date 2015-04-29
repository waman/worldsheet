package org.waman.worldsheet.simulation

import org.scalatest._
import org.waman.worldsheet.system.SystemNoParam
import org.waman.worldsheet.{FibonacciState, PhysicalSystem, PhysicalSimulation}


class StateAsDataTest extends FlatSpec with Matchers {
  
  class Simulation extends PhysicalSimulation
      with StateAsData with NoParam{

    override type State = FibonacciState

    override val physicalSystem = new PhysicalSystem with SystemNoParam{

      override type State = Simulation.this.State
      override type Param = Simulation.this.Param

      override val initialState: State = FibonacciState(0, 1)
      override val stateEvolver: State => State =
        s => FibonacciState(s.next, s.current + s.next)
    }
  }

  "A StateAsData" should "return equivalent Seq by newPhysicalProcess and by newDataSeq" in {
    val sim = new Simulation
    val pp = sim.newPhysicalProcess()
    val dataSeq = sim.newDataSeq()

    dataSeq.take(10) shouldBe pp.take(10)
  }
}