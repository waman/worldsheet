package org.waman.worldsheet.observer

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.OptionValues._
import org.waman.worldsheet.FibonacciState

class ObservableSetWithDataInfoTest extends FlatSpec with Matchers {

  abstract class AbstractObservableSet extends ObservableSetWithDataInfo[FibonacciState](
    Map("Current" -> classOf[Integer], "Next" -> classOf[BigInt])
  )

  class WellDefinedObservableSet extends AbstractObservableSet{
    override protected def calculate(state: FibonacciState): Map[String, Any] = {
      Map("Current" -> state.current, "Next" -> BigInt(state.next))
    }
  }

  "A ObservableSetWithDataInfo" should "be able to define well" in {
    val obs = new WellDefinedObservableSet

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

  class DataEntryMismatchObservableSet extends AbstractObservableSet{
    override protected def calculate(state: FibonacciState): Map[String, Any] = {
      Map("current" -> state.current, "next" -> BigInt(state.next))
    }
  }

  it should "validate data entries at observation" in {
    val obs = new DataEntryMismatchObservableSet

    intercept[ObservationExceptionTest]{
      val result = obs.observe(FibonacciState(5, 8))
    }
  }

  class DatatypeMismatchObservableSet extends AbstractObservableSet{
    override protected def calculate(state: FibonacciState): Map[String, Any] = {
      Map("Current" -> state.current, "Next" -> true)
    }
  }

  it should "validate datatype at observation" in {
    val obs = new DatatypeMismatchObservableSet

    intercept[DatatypeMismatchException]{
      val result = obs.observe(FibonacciState(5, 8))
    }
  }
}
