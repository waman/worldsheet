package org.waman.worldsheet.simulation

import java.nio.charset.Charset
import java.nio.file.Paths

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.outputter.{ConsoleOutputter, FileOutputter}
import org.waman.worldsheet.{FibonacciState, FibonacciSystem, PhysicalSimulation}


class StringDataTest extends FlatSpec with Matchers {

  abstract class StringDataSimulation extends PhysicalSimulation
      with NoParam with StringData{

    override type State = FibonacciState
    override val physicalSystem = new FibonacciSystem
  }

  //***** Observer *****
  "A StringData" should "have the observer which convert State object to String by toString method" in {
    val sim = new StringDataSimulation {}
    val observer = sim.observer

    observer(FibonacciState(5, 8)) shouldBe FibonacciState(5, 8).toString
  }

  it should "be able to customize observation by overriding observer" in {
    val sim = new StringDataSimulation{
      override val observer = (s:FibonacciState) =>
        "(" + s.current + ", " + s.next + ")"
    }
    val observer = sim.observer

    observer(FibonacciState(5, 8)) shouldBe "(5, 8)"
  }

  //***** DataOutputter *****
  it should "create ConsoleOutputter by console()" in {
    val sim = new StringDataSimulation {
      override protected def outputters = List(console())
    }

    val out = sim.outputterProviders.head(())
    out shouldBe a [ConsoleOutputter[_]]

    val console = out.asInstanceOf[ConsoleOutputter[String]]
    console.formatter("string") shouldBe "string"
  }

  it should "create ConsoleOutputter with header by console(header:String)" in {
    val sim = new StringDataSimulation {
      override protected def outputters = List(console("[header]"))
    }

    val out = sim.outputterProviders.head(())
    out shouldBe a [ConsoleOutputter[_]]

    val console = out.asInstanceOf[ConsoleOutputter[String]]
    console.formatter("string") shouldBe "[header] string"
  }

  it should "create FileOutputter by file(fileName:String)" in {
    val sim = new StringDataSimulation {
      override protected def outputters = List(file("log1.txt"))
    }

    val out = sim.outputterProviders.head(())
    out shouldBe a [FileOutputter[_]]

    val file = out.asInstanceOf[FileOutputter[String]]
    file.path shouldBe Paths.get("log1.txt")
  }

  it should "create FileOutputter by file(fileName:String, charset, isOverride, )" in {
    val sim = new StringDataSimulation {
      override protected def outputters = List(file("log2.txt", Charset.forName("UTF-8"), isOverride=true))
    }

    val out = sim.outputterProviders.head(())
    out shouldBe a [FileOutputter[_]]

    val file = out.asInstanceOf[FileOutputter[String]]
    file should have(
      'path (Paths.get("log2.txt")),
      'charset (Charset.forName("UTF-8")),
      'override (true)
    )
  }
}