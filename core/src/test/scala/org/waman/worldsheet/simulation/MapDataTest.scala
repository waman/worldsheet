package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.Paths

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.OptionValues._
import org.waman.worldsheet.observer.{ObservableSet, Observable, MapObserver}
import org.waman.worldsheet.outputter.{FileOutputter, ConsoleOutputter}
import org.waman.worldsheet.{DataOutputter, PhysicalSimulation, FibonacciState, FibonacciSystem}

class MapDataTest extends FlatSpec with Matchers {

  //***** ObservableSet *****
  abstract class SampleSimulationForObserver extends PhysicalSimulation with NoParam with MapData{
    override type State = FibonacciState
    override val physicalSystem = new FibonacciSystem
  }

  def extractObservableSet(sim:SampleSimulationForObserver):ObservableSet[FibonacciState] = {
    sim.observer shouldBe a [MapObserver[_]]
    val observer = sim.observer

    observer.observableSets.head shouldBe a [ObservableSet[_]]
    observer.observableSets.head.asInstanceOf[ObservableSet[FibonacciState]]
  }

  "A MapData" should "create an ObservableSet with data info by observableSet(Map)(Function)" in {
    val sim = new SampleSimulationForObserver{
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
    val sim = new SampleSimulationForObserver{
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
    val sim = new SampleSimulationForObserver{
      override protected def observableSets = List(
        observableSet{ s =>
          Map("current" -> s.current, "next" -> BigInt(s.next))
        }
      )
    }

    val obs = extractObservableSet(sim)

    an [UnsupportedOperationException] should be thrownBy { obs.dataEntries }
    an [UnsupportedOperationException] should be thrownBy { obs.supportDataEntry("current") }
    an [UnsupportedOperationException] should be thrownBy { obs.getDatatype("current") }
  }

  def extractObservable(sim:SampleSimulationForObserver):Observable[FibonacciState, Integer] = {
    sim.observer shouldBe a [MapObserver[_]]
    val observer = sim.observer

    observer.observableSets.head shouldBe a [Observable[_,_]]
    observer.observableSets.head.asInstanceOf[Observable[FibonacciState, Integer]]
  }

  it should "create an Observable with datatype by observable(String, Class)(Function)" in {
    val sim = new SampleSimulationForObserver{
      override protected def observableSets = List(
        observable("typed value", classOf[Integer]){ s => s.current }
      )
    }

    val obs = extractObservable(sim)
    obs.dataEntries shouldBe Set("typed value")
    obs.getDatatype("typed value").value shouldBe classOf[Integer]
  }

  it should "create an Observable without datatype by observable(String)(Function)" in {
    val sim = new SampleSimulationForObserver{
      override protected def observableSets = List(
        observable("typeless value"){ s => s.current }
      )
    }

    val obs = extractObservable(sim)

    obs.dataEntries shouldBe Set("typeless value")
    obs.getDatatype("typeless value").value shouldBe classOf[Any]
  }
  
  //***** DataOutputter *****
  abstract class SampleSimulationForDataOutputter
      extends SampleSimulationForObserver with OneOutput {

    override protected def observableSets: List[ObservableSet[State]] = List(
      observableSet(Map("current" -> classOf[Integer], "next" -> classOf[Integer])) {
        s => Map("current" -> s.current, "next" -> s.next)
      }
    )
  }

  //*** ConsoleOutputter
    private def extractConsoleOutputter(sim:SampleSimulationForDataOutputter):ConsoleOutputter[Map[String,Any]] = {
      sim.outputter shouldBe a [ConsoleOutputter[_]]
      sim.outputter.asInstanceOf[ConsoleOutputter[Map[String,Any]]]
    }


    it should "create ConsoleOutputter by console() : all data entries are outputted when 'dataEntries' is not specified" in {
      val sim = new SampleSimulationForDataOutputter {
        override val outputter: DataOutputter[Data] = console()
      }

    val formatter = extractConsoleOutputter(sim).formatter
    formatter(Map("current" -> 0, "next" -> 1)) shouldBe ( (" "*19) + "0" + (" "*20) + "1")
  }
  
  it should "create ConsoleOutputter by console(dataEntries:List) with dataEntries specified" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = console(List("current"))
    }

    val formatter = extractConsoleOutputter(sim).formatter
    formatter(Map("current" -> 0)) shouldBe ( (" "*19) + "0" )
  }

  it should "create ConsoleOutputter by console(List, String, String, Int) : full arguments" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = console(List("current", "next"), "[header]", ", ", 10)
    }

    val formatter = extractConsoleOutputter(sim).formatter
    formatter(Map("current" -> 1, "next" -> 2)) shouldBe "[header]          1,          2"
  }

  it should "create ConsoleOutputter by console(pad=...) : short padding" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = console(List("current"), pad=5)
    }

    val formatter = extractConsoleOutputter(sim).formatter
    formatter(Map("current" -> 123456789)) shouldBe "1234_"
  }

  it should "throw IllegalArgumentException when console(pad=...) with non-positive number" in {
    an [IllegalArgumentException] should be thrownBy {
      val sim = new SampleSimulationForDataOutputter {
        override val outputter: DataOutputter[Data] = console(List("current"), pad = 0)
      }
    }
  }

  //*** FileOutputter
  private def extractFileOutputter(sim:SampleSimulationForDataOutputter):FileOutputter[Map[String,Any]] = {
    sim.outputter shouldBe a [FileOutputter[_]]
    sim.outputter.asInstanceOf[FileOutputter[Map[String,Any]]]
  }


  it should "create FileOutputter by file(Path, String) : all data entries are outputted when 'dataEntries' is not specified" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = file("log.txt", separator=":")
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.txt")),
      'charset (Charset.defaultCharset),
      'override (false)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0:1"
  }

  it should "create ConsoleOutputter by file(Path, List, String) with dataEntries specified" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = file("log.txt", List("current"), separator=":")
    }

    val formatter = extractFileOutputter(sim).formatter
    formatter(Map("current" -> 0)) shouldBe "0"
  }

  it should "create ConsoleOutputter by file(Path, List, String, String, Int) : full arguments" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] =
        file("log.txt", List("current", "next"), Charset.forName("UTF-8"), isOverride=true, "-")
    }

    val outputter = extractFileOutputter(sim)
    outputter should have (
      'path (Paths.get("log.txt")),
      'charset (Charset.forName("UTF-8")),
      'override (true)
    )
    outputter.formatter(Map("current" -> 1, "next" -> 2)) shouldBe "1-2"
  }

  //*** ssv, tsv, csv
  it should "create FileOutputter by ssv(Path)" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = ssv("log.ssv")
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.ssv")),
      'charset (Charset.defaultCharset),
      'override (false)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0 1"
  }

  it should "create FileOutputter by ssv(Path, List, Charset, Boolean) : full arguments" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] =
        ssv("log.ssv", List("current", "next"), Charset.forName("UTF-8"), isOverride=true)
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.ssv")),
      'charset (Charset.defaultCharset),
      'override (true)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0 1"
  }

  it should "create FileOutputter by tsv(Path)" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = tsv("log.tsv")
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.tsv")),
      'charset (Charset.defaultCharset),
      'override (false)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0\t1"
  }

  it should "create FileOutputter by tsv(Path, List, Charset, Boolean) : full arguments" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] =
        tsv("log.tsv", List("current", "next"), Charset.forName("UTF-8"), isOverride=true)
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.tsv")),
      'charset (Charset.defaultCharset),
      'override (true)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0\t1"
  }

  it should "create FileOutputter by csv(Path)" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] = csv("log.csv")
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.csv")),
      'charset (Charset.defaultCharset),
      'override (false)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0,1"
  }

  it should "create FileOutputter by csv(Path, List, Charset, Boolean) : full arguments" in {
    val sim = new SampleSimulationForDataOutputter {
      override val outputter: DataOutputter[Data] =
        csv("log.csv", List("current", "next"), Charset.forName("UTF-8"), isOverride=true)
    }

    val outputter = extractFileOutputter(sim)
    outputter should have(
      'path (Paths.get("log.csv")),
      'charset (Charset.defaultCharset),
      'override (true)
    )
    outputter.formatter(Map("current" -> 0, "next" -> 1)) shouldBe "0,1"
  }
}