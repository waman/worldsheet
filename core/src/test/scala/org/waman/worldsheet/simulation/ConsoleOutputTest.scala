package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.AbstractFibonacciSimulation

class ConsoleOutputSimulation extends AbstractFibonacciSimulation
    with ConsoleOutput

class ConsoleOutputTest extends FlatSpec with Matchers {

  "A ConsoleOutput trait" should "be able to define well" in {
    new ConsoleOutputSimulation().simulateWhileState(_.current <= 100)
  }
}