package org.waman.worldsheet.observer

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.OptionValues._
import org.waman.worldsheet.FibonacciState

class ObservableSetWithDataInfoTest extends FlatSpec with Matchers {

  abstract class AbstractObservableSet extends ObservableSetWithDataInfo[FibonacciState](
    Map("Current" -> classOf[Integer], "Next" -> classOf[BigInt])
  )

  "A ObservableSetWithDataInfo" should "be able to define well" in {
    val obs = new AbstractObservableSet{
      override protected def calculate(state: FibonacciState): Map[String, Any] = {
        Map("Current" -> state.current, "Next" -> BigInt(state.next))
      }
    }

    obs.dataEntries should be (Set("Current", "Next"))

    obs.supportDataEntry("Current") shouldBe true
    obs.supportDataEntry("Next") shouldBe true
    obs.supportDataEntry("current") shouldBe false

    obs.getDatatype("Current").value should be (classOf[Integer])
    obs.getDatatype("Next").value should be (classOf[BigInt])
    obs.getDatatype("current") shouldBe None

    // observation
    val result = obs.observe(FibonacciState(5, 8))
    result.get("Current").value shouldBe 5
    result.get("Next").value shouldBe BigInt(8)
    result.get("current") shouldBe None
  }

  it should "validate data entries at observation" in {
    val obs = new AbstractObservableSet{
      override protected def calculate(state: FibonacciState): Map[String, Any] = {
        Map("current" -> state.current, "next" -> BigInt(state.next))
      }
    }

    intercept[DataEntryMismatchException]{
      obs.observe(FibonacciState(5, 8))
    }
  }

  it should "validate datatype at observation" in {
    val obs = new AbstractObservableSet{
      override protected def calculate(state: FibonacciState): Map[String, Any] = {
        Map("Current" -> state.current, "Next" -> true)
      }
    }

    intercept[DatatypeMismatchException]{
      obs.observe(FibonacciState(5, 8))
    }
  }

  it should "throw Exception when null or empty map is passed" in {
    an [IllegalArgumentException] should be thrownBy{
      new ObservableSetWithDataInfo[FibonacciState](null) {
        override protected def calculate(state: FibonacciState): Map[String, Any] = Map.empty
      }
    }

    an [IllegalArgumentException] should be thrownBy{
      new ObservableSetWithDataInfo[FibonacciState](Map.empty) {
        override protected def calculate(state: FibonacciState): Map[String, Any] = Map.empty
      }
    }
  }
}
