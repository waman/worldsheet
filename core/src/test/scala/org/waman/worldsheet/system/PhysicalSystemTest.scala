package org.waman.worldsheet.system

import org.scalatest._
import org.waman.worldsheet.{FibonacciState, PhysicalSystem}

class GeneralizedFibonacciSystem extends PhysicalSystem{

  type State = FibonacciState
  type Param = (Int, Int)

  def newInitialState(param: (Int, Int)) = FibonacciState(param._1, param._2)

  def newStateEvolver(param: (Int, Int)) = s => FibonacciState(s.next, s.current + s.next)
}

class PhysicalSystemTest extends FlatSpec with Matchers {

  "A PhysicalSystem" should "be able to define well" in {
    val series = new GeneralizedFibonacciSystem().newPhysicalProcess(1, 2)
    series.map(_.current).take(10) shouldBe Seq(1, 2, 3, 5, 8, 13, 21, 34, 55, 89)
  }
}