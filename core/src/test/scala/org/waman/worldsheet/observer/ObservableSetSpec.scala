package org.waman.worldsheet.observer

import org.scalatest.{OptionValues, Matchers, FlatSpec}
import org.waman.worldsheet.FibonacciState
import OptionValues._

class ObservableSetSpec extends FlatSpec with Matchers{

  "A ObservableSet" should "create ObservableSetWithDataInfo for Map and Function" in {
    val obs = ObservableSet.createObservableSet(Map("Current" -> classOf[Integer], "Next" -> classOf[Integer])){
      (state:FibonacciState) => Map("Current" -> state.current, "Next" -> state.next)
    }

    obs shouldBe an [ObservableSetWithDataInfo[_]]

    obs.dataEntries should be (Set("Current", "Next"))
    obs.getDatatype("Current").value should be (classOf[Integer])
    obs.getDatatype("Next").value should be (classOf[Integer])
  }

  it should "create ObservableSetWithoutDatatype for Seq[String] and Function" in {
    val obs = ObservableSet.createObservableSet("Current", "Next"){
      (state:FibonacciState) => Map("Current" -> state.current, "Next" -> state.next)
    }

    obs shouldBe an [ObservableSetWithoutDatatype[_]]

    obs.dataEntries should be (Set("Current", "Next"))
    obs.getDatatype("Current").value should be (classOf[Any])
    obs.getDatatype("Next").value should be (classOf[Any])
  }

  it should "create ObservableSetWithoutDataInfo for Function" in {
    val obs = ObservableSet.createObservableSet{
      (state:FibonacciState) => Map("Current" -> state.current, "Next" -> state.next)
    }

    obs shouldBe an [ObservableSetWithoutDataInfo[_]]
  }

  it should "create Observable for String, Class[_] and Function" in {
    val obs = ObservableSet.createObservable("Value", classOf[Integer]){
      (state:FibonacciState) => state.current
    }

    obs shouldBe an [Observable[_,_]]

    obs.dataEntries should be (Set("Value"))
    obs.getDatatype("Value").value should be (classOf[Integer])
  }

  it should "create Observable for String and Function" in {
    val obs = ObservableSet.createObservable("Value"){
      (state:FibonacciState) => state.current
    }

    obs shouldBe an [Observable[_,_]]

    obs.dataEntries should be (Set("Value"))
    obs.getDatatype("Value").value should be (classOf[Any])
  }
}