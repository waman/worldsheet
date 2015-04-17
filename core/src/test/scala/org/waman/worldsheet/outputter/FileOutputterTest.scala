package org.waman.worldsheet.outputter

import java.nio.charset.Charset
import java.nio.file.Paths

import org.scalatest.{FlatSpec, Matchers}
import org.waman.worldsheet.{DataOutputter, AbstractFibonacciSimulation}

class FileOutputterSimulation extends AbstractFibonacciSimulation{

  override protected val outputters: List[DataOutputter[Data]] = List(
    ConsoleOutputter(),
    FileOutputter(Paths.get("./fib.txt"), Charset.forName("UTF-8"), true, _.toString)
  )
}

class FileOutputterTest extends FlatSpec with Matchers {

  "A FileOutputter trait" should "be able to define well" in {
    new FileOutputterSimulation().simulateWhileState(_.current <= 100)
  }
}