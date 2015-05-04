package org.waman.worldsheet.sample.radioactive

import org.waman.worldsheet.simulation.{ConsoleOutput, NoParam}
import org.waman.worldsheet.system.SystemNoParam
import org.waman.worldsheet.{PhysicalSimulation, PhysicalSystem, StateEvolver}
import spire.math.Rational
import spire.syntax.literals._

case class RadioActiveState(t:Rational, n:Double)

case class RadioActiveEvolver(dt:Rational, lambda:Double)
  extends StateEvolver[RadioActiveState]{

  override def apply(system: RadioActiveState): RadioActiveState =
    RadioActiveState(
      system.t + dt,
      system.n - lambda * system.n * dt.doubleValue()
    )
}

class RadioActiveSystem(n0: Double, lambda: Double)
    extends PhysicalSystem with SystemNoParam{

  override type State = RadioActiveState
  override val initialState = RadioActiveState(r"0.0", n0)
  override val stateEvolver = RadioActiveEvolver(r"0.1", lambda)
}

case class RadioActiveData(time:Rational, numberOfNuclei:Double)

class RadioActiveSimulation extends PhysicalSimulation with NoParam with ConsoleOutput{

  type State = RadioActiveState
  type Data = RadioActiveData

  val physicalSystem = new RadioActiveSystem(1.0e6, Math.log(2.0)/5.0)
  val observer = (s: State) => new Data(s.t, s.n)
}