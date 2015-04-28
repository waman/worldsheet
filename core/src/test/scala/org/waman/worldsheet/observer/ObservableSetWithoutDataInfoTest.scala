package org.waman.worldsheet.observer

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.FibonacciState

class ObservableSetWithoutDataInfoTest extends FlatSpec with Matchers {

  "A ObservableSetWithoutDataInfo" should "be able to define well" in {
    val obs = new ObservableSetWithoutDataInfo[FibonacciState]{
      override def apply(state: FibonacciState): Map[String, Any] = {
        Map("Current" -> state.current, "Next" -> state.next)
      }
    }
  }
}
