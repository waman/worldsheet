package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.{FibonacciState, FibonacciSystem}

class MapDataSimulation(fileName:String) extends NoParam with MapData{

  override type State = FibonacciState

  override val physicalSystem = new FibonacciSystem

  override protected val observableSets = List(
    observable("typeless value"){ s => s.current },
    observable("typed value", classOf[Int]){ s => s.current },
    observableSet("current", "next"){ s => Map("current" -> s.current, "next" -> s.next) }
  )

  override protected val outputters = List(
    console(),
    ssv(fileName, isOverride=true)
  )
}

class MapDataTest extends FlatSpec with Matchers {

  "A MapData" should "be able to define well" in {
    val fileName = System.getProperty("user.home") + "/fib.txt"
    new MapDataSimulation(fileName).simulateWhileState(_.current <= 100)
  }
}