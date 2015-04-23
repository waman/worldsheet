package org.waman.worldsheet.simulation

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.{FibonacciState, FibonacciSystem}

class StringDataSimulation(fileName:String) extends NoParam with StringData{

  override type State = FibonacciState

  override val physicalSystem = new FibonacciSystem

  override protected val outputters = List(
    console(),
    file(fileName, isOverride=true)
  )
}

class StringDataTest extends FlatSpec with Matchers {

  "A StringData" should "be able to define well" in {
    val fileName = System.getProperty("user.home") + "/fib.txt"
    new StringDataSimulation(fileName).simulateWhileState(_.current <= 100)
  }
}