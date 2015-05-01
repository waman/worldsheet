package org.waman.worldsheet.observer

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.FibonacciState

class ObservableSetWithoutDataInfoTest extends FlatSpec with Matchers {

  class WellDefinedObservableSet extends ObservableSetWithoutDataInfo[FibonacciState]{
    override def apply(state: FibonacciState): Map[String, Any] = {
      Map("Current" -> state.current, "Next" -> BigInt(state.next))
    }
  }

  "A ObservableSetWithoutDataInfo" should "be able to define well" in {
    val obs = new WellDefinedObservableSet

    an [UnsupportedOperationException] should be thrownBy { obs.dataEntries }
    an [UnsupportedOperationException] should be thrownBy { obs.supportDataEntry("Current") }
    an [UnsupportedOperationException] should be thrownBy { obs.supportDataEntry("current") }
    an [UnsupportedOperationException] should be thrownBy { obs.getDatatype("Current") }
    an [UnsupportedOperationException] should be thrownBy { obs.getDatatype("current") }

    obs.observe(FibonacciState(5, 8)) shouldBe Map("Current" -> 5, "Next" -> BigInt(8))
  }
}
