package org.waman.worldsheet.outputter

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.{AbstractFibonacciSimulation, DataOutputter}

class FileOutputterSimulation(fileName:String) extends AbstractFibonacciSimulation{

  override protected val outputters: List[DataOutputter[Data]] = List(
    ConsoleOutputter(dataFormatter),
    FileOutputter(fileName, isOverride=true)
  )
}

class FileOutputterTest extends FlatSpec with Matchers {

  "A FileOutputter" should "be able to define well" in {
    val fileName = System.getProperty("user.home")+"/fib.txt"
    new FileOutputterSimulation(fileName).simulateWhileState(_.current <= 100)
  }
}