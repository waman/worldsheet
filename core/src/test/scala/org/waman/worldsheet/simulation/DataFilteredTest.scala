package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}

class DataFilteredSimulation extends AbstractFibonacciSimulation
    with DataFiltered with NoOutput{

  type Data = Int
  val observer = (s:State) => s.current

  val dataFilter = (data:Data) => data % 2 == 1
}

class DataFilteredTest extends FlatSpec with Matchers {

  "A FilterData trait" should "be able to define well" in {
    val series = new DataFilteredSimulation().newDataSeq(0, 1)
    series.take(5) should be (Seq(1, 1, 3, 5, 13))
  }
}