package org.waman.worldsheet.simulation

import org.scalatest._
import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem}

case class FibonacciState(no: Int, current: Int, next: Int)

class FibonacciSystem extends PhysicalSystem{

  override type State = FibonacciState
  override type Params = (Int, Int)

  override protected
  def newInitialState(params:Params) = FibonacciState(0, params._1, params._2)

  override protected
  def newStateEvolver(params:Params) = s => FibonacciState(s.no + 1, s.next, s.current + s.next)
}

class FibonacciSimulation extends PhysicalSimulation with StateAsData {

  override type State = FibonacciState
  override type Params = (Int, Int)

  override val physicalSystem = new FibonacciSystem
}

class PhysicalSystemTest extends FlatSpec with Matchers {

  "A StateAsData" should "be able to define well" in {
    val series = new FibonacciSimulation().createDataStream(1, 2)
    series.map(_.current).take(5) should be (Seq(1, 2, 3, 5, 8))
  }
}