package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}

class DataFilteredSimulation extends AbstractFibonacciSimulation
    with DataFiltered{

  override type Data = Int
  override val observer = (s:State) => s.current

  override val dataFilter = (data:Data) => data % 2 == 1
}

class DataFilteredTest extends FlatSpec with Matchers {

  "A FilterData trait" should "be able to define well" in {
    val series = new DataFilteredSimulation().newDataSeq()
    series.take(5) should be (Seq(1, 1, 3, 5, 13))
  }
}