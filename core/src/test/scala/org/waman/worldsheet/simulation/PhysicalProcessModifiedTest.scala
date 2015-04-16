package org.waman.worldsheet.simulation

import org.scalatest.{Matchers, FlatSpec}

class PhysicalProcessModifiedSimulation extends AbstractFibonacciSimulation
    with PhysicalProcessModified{

  override def modifyPhysicalProcess(s:Seq[State]) = s.take(5)
}

class PhysicalProcessModifiedTest extends FlatSpec with Matchers {

  "A PhysicalProcessModified trait" should "be able to define well" in {
    val series = new PhysicalProcessModifiedSimulation().newDataSeq()
    series should be (Seq(0, 1, 1, 2, 3))
  }
}