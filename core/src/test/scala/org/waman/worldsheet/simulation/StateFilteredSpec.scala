package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.AbstractFibonacciSimulation

class StateFilteredSpec extends FlatSpec with Matchers {

  "A FilterState" should "be able to define well" in {

    val sim = new AbstractFibonacciSimulation with StateFiltered{
      override val stateFilter = (s:State) => s.current % 2 == 0
    }

    sim.newDataSeq().take(5) shouldBe Seq(0, 2, 8, 34, 144)
  }
}