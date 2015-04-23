package org.waman.worldsheet.outputter

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.AbstractFibonacciSimulation
import org.waman.worldsheet.simulation.{DataOutputterFactory, StringData}

class FileOutputterSimulation(fileName:String)
    extends AbstractFibonacciSimulation with DataOutputterFactory{

  override protected val outputters = List(
    newConsoleOutputter(),
    newFileOutputter(fileName, isOverride=true)
  )
}

class FileOutputterTest extends FlatSpec with Matchers {

  "A FileOutputter" should "be able to define well" in {
    val fileName = System.getProperty("user.home")+"/fib.txt"
    new FileOutputterSimulation(fileName).simulateWhileState(_.current <= 100)
  }
}