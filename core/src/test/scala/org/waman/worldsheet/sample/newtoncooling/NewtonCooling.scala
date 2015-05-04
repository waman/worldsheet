package org.waman.worldsheet.sample.newtoncooling

import org.waman.worldsheet.simulation.ConsoleOutput
import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem}
import spire.math.Rational
import spire.syntax.literals._

case class NewtonCoolingState(time:Rational, temper:Double)

class NewtonCoolingSystem extends PhysicalSystem {

  type State = NewtonCoolingState
  type Param = (Double, Rational, Double) // (temper0, dt, temperEx)

  protected def newInitialState(param: (Double, Rational, Double)) = NewtonCoolingState(r"0.1", param._1)

  protected def newStateEvolver(param: (Double, Rational, Double)) = {
    val (_, dt, temperEx) = param

    val dTime = d("time", dt)
    val dTemper = d("temper") { (c: State, n: State, p: Param) =>
      -0.03 * (c.temper - temperEx) * dt.toDouble
    }

    current => {
      var next = current.copy()
      next = next.copy(time = dTime(current, next, param))
      next = next.copy(temper = dTemper(current, next, param))
      next
    }
  }
}

case class NewtonCoolingData(time:Rational, temperature:Double)

class NewtonCoolingSimulation extends PhysicalSimulation with ConsoleOutput{

  type State = NewtonCoolingState
  type Param = (Double, Rational, Double)  // (temper0, dt, temperEx)
  type Data = NewtonCoolingData

  val physicalSystem = new NewtonCoolingSystem
  val observer = (s:State) => new Data(s.time, s.temper)
}
