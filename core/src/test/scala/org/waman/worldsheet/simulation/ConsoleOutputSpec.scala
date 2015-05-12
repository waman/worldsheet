package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.AbstractFibonacciSimulation
import org.waman.worldsheet.outputter.ConsoleOutputter

class ConsoleOutputSpec extends FlatSpec with Matchers {

  class Simulation extends AbstractFibonacciSimulation
    with ConsoleOutput

  "A ConsoleOutput" should "have only one ConsoleOutputter as outputter" in {
    val sim = new Simulation

    sim.outputter shouldBe a [ConsoleOutputter[_]]
    sim.outputterProviders.head(()) shouldBe a [ConsoleOutputter[_]]
  }
}