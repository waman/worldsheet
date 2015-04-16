package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}

class DataSeqModifiedSimulation extends AbstractFibonacciSimulation
    with DataSeqModified{

  override def modifyDataSeq(s:Seq[Data]) = s.take(6)
}

class DataSeqModifiedTest extends FlatSpec with Matchers {

  "A DataSeqModified trait" should "be able to define well" in {
    val series = new DataSeqModifiedSimulation().newDataSeq()
    series should be (Seq(0, 1, 1, 2, 3, 5))
  }
}