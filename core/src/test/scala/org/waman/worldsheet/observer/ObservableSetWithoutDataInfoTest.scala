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

    intercept[UnsupportedOperationException]{ obs.dataEntries }
    intercept[UnsupportedOperationException]{ obs.supportDataEntry("Current") }
    intercept[UnsupportedOperationException]{ obs.supportDataEntry("current") }
    intercept[UnsupportedOperationException]{ obs.getDatatype("Current") }
    intercept[UnsupportedOperationException]{ obs.getDatatype("current") }

    obs.observe(FibonacciState(5, 8)) shouldBe Map("Current" -> 5, "Next" -> BigInt(8))
  }
}
