package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.AbstractFibonacciSimulation

class DataFilteredSpec extends FlatSpec with Matchers {

  "A FilterData" should "be able to define well" in {

    val sim = new AbstractFibonacciSimulation with DataFiltered{
      override val dataFilter = (data:Data) => data % 2 == 1
    }

    sim.newDataSeq().take(5) shouldBe Seq(1, 1, 3, 5, 13)
  }
}