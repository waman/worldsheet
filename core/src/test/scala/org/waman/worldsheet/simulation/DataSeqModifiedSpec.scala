package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}
import org.waman.worldsheet.AbstractFibonacciSimulation

class DataSeqModifiedSpec extends FlatSpec with Matchers {

  "A DataSeqModified" should "be able to define well" in {

    val sim = new AbstractFibonacciSimulation with DataSeqModified{
      override def modifyDataSeq(s:Seq[Data]) = s.take(6)
    }

    sim.newDataSeq() shouldBe Seq(0, 1, 1, 2, 3, 5)
  }
}