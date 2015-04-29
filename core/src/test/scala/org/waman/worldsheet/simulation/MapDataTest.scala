package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.OptionValues._
import org.waman.worldsheet.observer.{ObservableSet, Observable, MapObserver}
import org.waman.worldsheet.{PhysicalSimulation, FibonacciState, FibonacciSystem}

class MapDataTest extends FlatSpec with Matchers {

  abstract class Simulation extends PhysicalSimulation with NoParam with MapData{
    override type State = FibonacciState
    override val physicalSystem = new FibonacciSystem
  }

  //***** ObservableSet *****
  def extractObservableSet(sim:Simulation):ObservableSet[FibonacciState] = {
    sim.observer shouldBe a [MapObserver[_]]
    val observer = sim.observer

    observer.observableSets.head shouldBe a [ObservableSet[_]]
    observer.observableSets.head.asInstanceOf[ObservableSet[FibonacciState]]
  }

  "A MapData" should "create an ObservableSet with data info by observableSet(Map)(Function)" in {
    val sim = new Simulation{
      override protected def observableSets = List(
        observableSet(Map("typed current" -> classOf[Integer], "typed next" -> classOf[BigInt])){ s =>
          Map("typed current" -> s.current, "typed next" -> BigInt(s.next))
        }
      )
    }

    val obs = extractObservableSet(sim)
    obs.dataEntries shouldBe Set("typed current", "typed next")
    obs.getDatatype("typed current").value shouldBe classOf[Integer]
    obs.getDatatype("typed next").value shouldBe classOf[BigInt]
  }

  it should "create an ObservableSet without datatype by observableSet(String*)(Function)" in {
    val sim = new Simulation{
      override protected def observableSets = List(
        observableSet("typed current", "typed next"){ s =>
          Map("typeless current" -> s.current, "typeless next" -> BigInt(s.next))
        }
      )
    }

    val obs = extractObservableSet(sim)

    obs.dataEntries shouldBe Set("typed current", "typed next")
    obs.getDatatype("typed current").value shouldBe classOf[Any]
    obs.getDatatype("typed next").value shouldBe classOf[Any]
  }

  it should "create an ObservableSet without data info by observableSet(Function)" in {
    val sim = new Simulation{
      override protected def observableSets = List(
        observableSet{ s =>
          Map("current" -> s.current, "next" -> BigInt(s.next))
        }
      )
    }

    val obs = extractObservableSet(sim)

    intercept[UnsupportedOperationException]{ obs.dataEntries }
    intercept[UnsupportedOperationException]{ obs.supportDataEntry("current") }
    intercept[UnsupportedOperationException]{ obs.getDatatype("current") }
  }

  def extractObservable(sim:Simulation):Observable[FibonacciState, Integer] = {
    sim.observer shouldBe a [MapObserver[_]]
    val observer = sim.observer

    observer.observableSets.head shouldBe a [Observable[_,_]]
    observer.observableSets.head.asInstanceOf[Observable[FibonacciState, Integer]]
  }

  it should "create an Observable with datatype by observable(String, Class)(Function)" in {
    val sim = new Simulation{
      override protected def observableSets = List(
        observable("typed value", classOf[Integer]){ s => s.current }
      )
    }

    val obs = extractObservable(sim)
    obs.dataEntries shouldBe Set("typed value")
    obs.getDatatype("typed value").value shouldBe classOf[Integer]
  }

  "A MapData" should "create an Observable without datatype by observable(String)(Function)" in {
    val sim = new Simulation{
      override protected def observableSets = List(
        observable("typeless value"){ s => s.current }
      )
    }

    val obs = extractObservable(sim)

    obs.dataEntries shouldBe Set("typeless value")
    obs.getDatatype("typeless value").value shouldBe classOf[Any]
  }
}