package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}

class StateFilteredSimulation extends AbstractFibonacciSimulation
    with StateFiltered{

  override type Data = Int

  override val observer = (s:State) => s.current

  override val stateFilter = (s:State) => s.current % 2 == 0
}

class StateFilteredTest extends FlatSpec with Matchers {

  "A FilterState trait" should "be able to define well" in {
    val series = new StateFilteredSimulation().newDataSeq()
    series.take(5) should be (Seq(0, 2, 8, 34, 144))
  }
}