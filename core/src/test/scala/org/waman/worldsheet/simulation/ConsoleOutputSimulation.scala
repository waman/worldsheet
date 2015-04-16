package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}

class ConsoleOutputSimulation extends AbstractFibonacciSimulation
    with ConsoleOutput

class ConsoleOutputTest extends FlatSpec with Matchers {

  "A ConsoleOutput trait" should "be able to define well" in {
    new ConsoleOutputSimulation().simulateWhileStateIs(_.current <= 100)
  }
}