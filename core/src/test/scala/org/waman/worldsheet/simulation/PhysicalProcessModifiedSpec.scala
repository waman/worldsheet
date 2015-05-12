package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.AbstractFibonacciSimulation

class PhysicalProcessModifiedSpec extends FlatSpec with Matchers {

  "A PhysicalProcessModified" should "be able to define well" in {

    val sim = new AbstractFibonacciSimulation with PhysicalProcessModified{
      override def modifyPhysicalProcess(s:Seq[State]) = s.take(5)
    }

    sim.newDataSeq() shouldBe Seq(0, 1, 1, 2, 3)
  }
}