package org.waman.worldsheet.simulation

import org.scalatest._
import org.waman.worldsheet.PhysicalSimulation

class StateAsDataSimulation extends PhysicalSimulation
    with StateAsData with NoOutput{

  override type State = FibonacciState
  override type Param = (Int, Int)

  override val physicalSystem = new FibonacciSystem
}

class StateAsDataTest extends FlatSpec with Matchers {

  "A StateAsData trait " should "be able to define well" in {
    val series = new StateAsDataSimulation().newDataSeq()
    series.map(_.current).take(5) should be (Seq(1, 2, 3, 5, 8))
  }
}