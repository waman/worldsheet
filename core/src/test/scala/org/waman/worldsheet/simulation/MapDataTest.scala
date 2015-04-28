package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.{FibonacciState, FibonacciSystem}

class MapDataSimulation(fileName:String) extends NoParam with MapData{

  override type State = FibonacciState

  override val physicalSystem = new FibonacciSystem

  override protected def observableSets = List(

    observable("typed value", classOf[Int]){ s => s.current },

    observable("typeless value"){ s => s.current },

    observableSet(Map("typed current" -> classOf[Integer], "typed next" -> classOf[Integer])){ s =>
      Map("typed current" -> s.current, "typed next" -> s.next)
    },

    observableSet("typeless current", "typeless next"){ s =>
      Map("typeless current" -> s.current, "typeless next" -> s.next)
    },

    observableSet{ s =>
      Map("infoless current" -> s.current, "infoless next" -> s.next)
    }
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